package co.marvel.math.data.source.remote.events.response

interface ListEventsResult

data class EventSucceedResult(
        val code: String,
        val status: String,
        val copyright: String,
        val eTag: String,
        val data: DataEventResult
): ListEventsResult

data class DataEventResult(
        val offset: Int,
        val limit: Int,
        val total: Int,
        val count: Int,
        val results: List<Event>
)

data class Event(
        val id: Int,
        val title: String,
        val description: String,
        val modified: String,
        val thumbnail: Thumbnail,
        val start: String?,
        val end: String?
)

data class Thumbnail(val url: String)

data class EventFailedResult(
        val code: String,
        val status: String
) : ListEventsResult