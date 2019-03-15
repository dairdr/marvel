package co.marvel.math.data.source.remote.creators.response

interface ListCreatorsResult

data class CreatorSucceedResult(
        val code: String,
        val status: String,
        val copyright: String,
        val eTag: String,
        val data: DataCreatorResult
): ListCreatorsResult

data class DataCreatorResult(
        val offset: Int,
        val limit: Int,
        val total: Int,
        val count: Int,
        val results: List<Creator>
)

data class Creator(
        val id: Int,
        val firstName: String,
        val middleName: String,
        val lastName: String,
        val fullName: String,
        val modified: String,
        val thumbnail: Thumbnail
)

data class Thumbnail(val url: String)

data class CreatorFailedResult(
        val code: String,
        val status: String
) : ListCreatorsResult