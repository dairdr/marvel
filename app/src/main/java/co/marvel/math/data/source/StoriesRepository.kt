package co.marvel.math.data.source

import co.marvel.math.data.source.remote.stories.response.FailedResponse
import co.marvel.math.data.source.remote.stories.StoryApi
import co.marvel.math.data.source.remote.stories.response.ListStoriesResponse
import co.marvel.math.data.source.remote.stories.response.ListStoriesResult
import co.marvel.math.utils.mapError
import co.marvel.math.utils.mapNetworkErrors
import co.marvel.math.utils.mapToDomain
import io.reactivex.Single
import retrofit2.Retrofit

class StoriesRepository(private val retrofit: Retrofit): StoriesDataSource {

    override fun stories(
            timestamp: String?,
            apiKey: String?,
            hash: String?,
            limit: Int?
    ): Single<ListStoriesResult> {
        val storyApi = retrofit.create(StoryApi::class.java)
        return storyApi
                .stories(timestamp, apiKey, hash, 20)
                .mapError<FailedResponse, ListStoriesResponse>()
                .mapNetworkErrors()
                .mapToDomain()
    }

    override fun story(
            id: Int?,
            timestamp: String?,
            apiKey: String?,
            hash: String?
    ): Single<ListStoriesResult> {
        val storyApi = retrofit.create(StoryApi::class.java)
        return storyApi
                .story(id, timestamp, apiKey, hash)
                .mapError<FailedResponse, ListStoriesResponse>()
                .mapNetworkErrors()
                .mapToDomain()
    }

    companion object {
        @Volatile
        private var instance: StoriesRepository? = null

        @JvmStatic
        fun default(retrofit: Retrofit): StoriesRepository =
                instance ?: synchronized(StoriesRepository::class.java) {

                    instance ?: StoriesRepository(retrofit).also {
                        instance = it
                    }
                }
    }
}