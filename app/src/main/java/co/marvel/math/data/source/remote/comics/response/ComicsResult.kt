package co.marvel.math.data.source.remote.comics.response

interface ListComicsResult

data class ComicSucceedResult(
        val code: String,
        val status: String,
        val copyright: String,
        val eTag: String,
        val data: DataComicResult
): ListComicsResult

data class DataComicResult(
        val offset: Int,
        val limit: Int,
        val total: Int,
        val count: Int,
        val results: List<Comic>
)

data class Comic(
        val id: Int,
        val title: String,
        val issueNumber: Int,
        val description: String?,
        val modified: String,
        val format: String,
        val pageCount: Int,
        val thumbnail: Thumbnail
)

data class Thumbnail(val url: String)

data class ComicFailedResult(
        val code: String,
        val status: String
) : ListComicsResult