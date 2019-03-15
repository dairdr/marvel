package co.marvel.math.data.source

import co.marvel.math.data.source.remote.comics.ComicApi
import co.marvel.math.data.source.remote.comics.response.FailedResponse
import co.marvel.math.data.source.remote.comics.response.ListComicsResponse
import co.marvel.math.data.source.remote.comics.response.ListComicsResult
import co.marvel.math.utils.mapError
import co.marvel.math.utils.mapNetworkErrors
import co.marvel.math.utils.mapToDomain
import io.reactivex.Single
import retrofit2.Retrofit

class ComicsRepository(private val retrofit: Retrofit): ComicsDataSource {

    override fun comics(
            timestamp: String?,
            apiKey: String?,
            hash: String?,
            limit: Int?
    ): Single<ListComicsResult> {
        val comicApi = retrofit.create(ComicApi::class.java)
        return comicApi
                .comics(timestamp, apiKey, hash, 20)
                .mapError<FailedResponse, ListComicsResponse>()
                .mapNetworkErrors()
                .mapToDomain()
    }

    override fun comic(
            id: Int?,
            timestamp: String?,
            apiKey: String?,
            hash: String?
    ): Single<ListComicsResult> {
        val comicApi = retrofit.create(ComicApi::class.java)
        return comicApi
                .comic(id, timestamp, apiKey, hash)
                .mapError<FailedResponse, ListComicsResponse>()
                .mapNetworkErrors()
                .mapToDomain()
    }

    companion object {
        @Volatile
        private var instance: ComicsRepository? = null

        @JvmStatic
        fun default(retrofit: Retrofit): ComicsRepository =
                instance ?: synchronized(ComicsRepository::class.java) {

                    instance ?: ComicsRepository(retrofit).also {
                        instance = it
                    }
                }
    }
}