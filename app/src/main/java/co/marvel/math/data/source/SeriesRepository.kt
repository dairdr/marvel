package co.marvel.math.data.source

import co.marvel.math.data.source.remote.series.SerieApi
import co.marvel.math.data.source.remote.series.response.FailedResponse
import co.marvel.math.data.source.remote.series.response.ListSeriesResponse
import co.marvel.math.data.source.remote.series.response.ListSeriesResult
import co.marvel.math.utils.mapError
import co.marvel.math.utils.mapNetworkErrors
import co.marvel.math.utils.mapToDomain
import io.reactivex.Single
import retrofit2.Retrofit

class SeriesRepository(private val retrofit: Retrofit): SeriesDataSource {

    override fun series(
            timestamp: String?,
            apiKey: String?,
            hash: String?,
            limit: Int?
    ): Single<ListSeriesResult> {
        val serieApi = retrofit.create(SerieApi::class.java)
        return serieApi
                .series(timestamp, apiKey, hash, 20)
                .mapError<FailedResponse, ListSeriesResponse>()
                .mapNetworkErrors()
                .mapToDomain()
    }

    override fun serie(
            id: Int?,
            timestamp: String?,
            apiKey: String?,
            hash: String?
    ): Single<ListSeriesResult> {
        val serieApi = retrofit.create(SerieApi::class.java)
        return serieApi
                .serie(id, timestamp, apiKey, hash)
                .mapError<FailedResponse, ListSeriesResponse>()
                .mapNetworkErrors()
                .mapToDomain()
    }

    companion object {
        @Volatile
        private var instance: SeriesRepository? = null

        @JvmStatic
        fun default(retrofit: Retrofit): SeriesRepository =
                instance ?: synchronized(SeriesRepository::class.java) {

                    instance ?: SeriesRepository(retrofit).also {
                        instance = it
                    }
                }
    }
}