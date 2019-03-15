package co.marvel.math.utils

import co.marvel.math.data.source.remote.ConnectionLostException
import co.marvel.math.data.source.remote.DomainMappable
import co.marvel.math.data.source.remote.NoNetworkException
import co.marvel.math.data.source.remote.ServerUnreachableException
import com.google.gson.Gson
import io.reactivex.Single
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Single<T>.mapNetworkErrors(): Single<T> =
        this.onErrorResumeNext { error ->
            when (error) {
                is SocketTimeoutException -> Single.error(NoNetworkException(error))
                is UnknownHostException -> Single.error(ServerUnreachableException(error))
                is ConnectException -> Single.error(ConnectionLostException(error))
                else -> Single.error(error)
            }
        }

inline fun <reified T : R, R> Single<out R>.mapError(): Single<R> =
        this.map { it }
                .onErrorResumeNext { error ->
                    if (error is HttpException && error.code() == 400) {
                        mapErrorBody(error, T::class.java)?.let {
                            Single.just(it)
                        } ?: Single.error(IllegalStateException("Error mapping http body"))
                    } else {
                        Single.error(error)
                    }
                }

fun <T : DomainMappable<R>, R> Single<T>.mapToDomain(): Single<R> =
        this.map {
            it.asDomain()
        }

fun <T> mapErrorBody(error: HttpException, type: Class<T>) =
        error.response().errorBody()?.let {
            Gson().fromJson(it.string(), type)
        }