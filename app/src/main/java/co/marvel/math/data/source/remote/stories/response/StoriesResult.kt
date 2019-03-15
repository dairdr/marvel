package co.marvel.math.data.source.remote.stories.response

interface ListStoriesResult

data class StorySucceedResult(
        val code: String,
        val status: String,
        val copyright: String,
        val eTag: String,
        val data: DataStoryResult
): ListStoriesResult

data class DataStoryResult(
        val offset: Int,
        val limit: Int,
        val total: Int,
        val count: Int,
        val results: List<Story>
)

data class Story(
        val id: Int,
        val title: String,
        val description: String,
        val modified: String,
        val thumbnail: Thumbnail?
)

data class Thumbnail(val url: String)

data class StoryFailedResult(
        val code: String,
        val status: String
) : ListStoriesResult