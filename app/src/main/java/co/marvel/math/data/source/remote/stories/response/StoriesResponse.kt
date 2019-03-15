package co.marvel.math.data.source.remote.stories.response

import co.marvel.math.data.source.remote.DomainMappable
import com.google.gson.annotations.SerializedName

interface ListStoriesResponse : DomainMappable<ListStoriesResult> {
    override fun asDomain(): ListStoriesResult
}

data class SucceedResponse(
        @SerializedName("code") val code: String,
        @SerializedName("status") val status: String,
        @SerializedName("copyright") val copyright: String,
        @SerializedName("etag") val eTag: String,
        @SerializedName("data") val data: DataStoryResponse
) : ListStoriesResponse {

    override fun asDomain(): ListStoriesResult {
        val results = data.results.map { story ->
            var thumbnail: Thumbnail? = null
            story.thumbnail?.let {
                thumbnail = Thumbnail(story.thumbnail.getPathWithExtension())
            }
           Story(
                   story.id,
                   story.title,
                   story.description,
                   story.modified,
                   thumbnail
           )
        }
        val data = DataStoryResult(data.offset, data.limit, data.total, data.count, results)
        return StorySucceedResult(code, status, copyright, eTag, data)
    }
}

data class DataStoryResponse(
        @SerializedName("offset") val offset: Int,
        @SerializedName("limit") val limit: Int,
        @SerializedName("total") val total: Int,
        @SerializedName("count") val count: Int,
        @SerializedName("results") val results: List<StoryResponse>
)

data class StoryResponse(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("modified") val modified: String,
        @SerializedName("thumbnail") val thumbnail: ThumbnailResponse?
)

data class ThumbnailResponse(
        @SerializedName("path") val path: String,
        @SerializedName("extension") val extension: String
) {
    fun getPathWithExtension() : String {
        return "$path.$extension"
    }
}

data class FailedResponse(
        @SerializedName("code") val code: String,
        @SerializedName("status") val status: String
) : ListStoriesResponse {

    override fun asDomain() = StoryFailedResult(code, status)
}