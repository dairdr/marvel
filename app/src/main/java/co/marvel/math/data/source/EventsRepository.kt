package co.marvel.math.data.source

import co.marvel.math.data.source.remote.events.EventApi
import co.marvel.math.data.source.remote.events.response.FailedResponse
import co.marvel.math.data.source.remote.events.response.ListEventsResponse
import co.marvel.math.data.source.remote.events.response.ListEventsResult
import co.marvel.math.utils.mapError
import co.marvel.math.utils.mapNetworkErrors
import co.marvel.math.utils.mapToDomain
import io.reactivex.Single
import retrofit2.Retrofit

class EventsRepository(private val retrofit: Retrofit): EventsDataSource {

    override fun events(
            timestamp: String?,
            apiKey: String?,
            hash: String?,
            limit: Int?
    ): Single<ListEventsResult> {
        val eventApi = retrofit.create(EventApi::class.java)
        return eventApi
                .events(timestamp, apiKey, hash, 20)
                .mapError<FailedResponse, ListEventsResponse>()
                .mapNetworkErrors()
                .mapToDomain()
    }

    override fun event(
            id: Int?,
            timestamp: String?,
            apiKey: String?,
            hash: String?
    ): Single<ListEventsResult> {
        val eventApi = retrofit.create(EventApi::class.java)
        return eventApi
                .event(id, timestamp, apiKey, hash)
                .mapError<FailedResponse, ListEventsResponse>()
                .mapNetworkErrors()
                .mapToDomain()
    }

    companion object {
        @Volatile
        private var instance: EventsRepository? = null

        @JvmStatic
        fun default(retrofit: Retrofit): EventsRepository =
                instance ?: synchronized(EventsRepository::class.java) {

                    instance ?: EventsRepository(retrofit).also {
                        instance = it
                    }
                }
    }
}