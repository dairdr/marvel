package co.marvel.math.data.source.remote.events.response

import co.marvel.math.data.source.remote.DomainMappable
import com.google.gson.annotations.SerializedName

interface ListEventsResponse : DomainMappable<ListEventsResult> {
    override fun asDomain(): ListEventsResult
}

data class SucceedResponse(
        @SerializedName("code") val code: String,
        @SerializedName("status") val status: String,
        @SerializedName("copyright") val copyright: String,
        @SerializedName("etag") val eTag: String,
        @SerializedName("data") val data: DataEventResponse
) : ListEventsResponse {

    override fun asDomain(): ListEventsResult {
        val results = data.results.map { event ->
            val thumbnail = Thumbnail(event.thumbnail.getPathWithExtension())
           Event(
                   event.id,
                   event.title,
                   event.description,
                   event.modified,
                   thumbnail,
                   event.start,
                   event.end
           )
        }
        val data = DataEventResult(data.offset, data.limit, data.total, data.count, results)
        return EventSucceedResult(code, status, copyright, eTag, data)
    }
}

data class DataEventResponse(
        @SerializedName("offset") val offset: Int,
        @SerializedName("limit") val limit: Int,
        @SerializedName("total") val total: Int,
        @SerializedName("count") val count: Int,
        @SerializedName("results") val results: List<EventResponse>
)

data class EventResponse(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("modified") val modified: String,
        @SerializedName("thumbnail") val thumbnail: ThumbnailResponse,
        @SerializedName("start") val start: String?,
        @SerializedName("end") val end: String?
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
) : ListEventsResponse {

    override fun asDomain() = EventFailedResult(code, status)
}