package co.marvel.math.data.source

import co.marvel.math.data.source.remote.creators.CreatorApi
import co.marvel.math.data.source.remote.creators.response.FailedResponse
import co.marvel.math.data.source.remote.creators.response.ListCreatorsResponse
import co.marvel.math.data.source.remote.creators.response.ListCreatorsResult
import co.marvel.math.utils.mapError
import co.marvel.math.utils.mapNetworkErrors
import co.marvel.math.utils.mapToDomain
import io.reactivex.Single
import retrofit2.Retrofit

class CreatorsRepository(private val retrofit: Retrofit): CreatorsDataSource {

    override fun creators(
            timestamp: String?,
            apiKey: String?,
            hash: String?,
            limit: Int?
    ): Single<ListCreatorsResult> {
        val creatorApi = retrofit.create(CreatorApi::class.java)
        return creatorApi
                .creators(timestamp, apiKey, hash, 20)
                .mapError<FailedResponse, ListCreatorsResponse>()
                .mapNetworkErrors()
                .mapToDomain()
    }

    override fun creator(
            id: Int?,
            timestamp: String?,
            apiKey: String?,
            hash: String?
    ): Single<ListCreatorsResult> {
        val creatorApi = retrofit.create(CreatorApi::class.java)
        return creatorApi
                .creator(id, timestamp, apiKey, hash)
                .mapError<FailedResponse, ListCreatorsResponse>()
                .mapNetworkErrors()
                .mapToDomain()
    }

    companion object {
        @Volatile
        private var instance: CreatorsRepository? = null

        @JvmStatic
        fun default(retrofit: Retrofit): CreatorsRepository =
                instance ?: synchronized(CreatorsRepository::class.java) {

                    instance ?: CreatorsRepository(retrofit).also {
                        instance = it
                    }
                }
    }
}