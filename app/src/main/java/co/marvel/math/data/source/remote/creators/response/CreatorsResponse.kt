package co.marvel.math.data.source.remote.creators.response

import co.marvel.math.data.source.remote.DomainMappable
import com.google.gson.annotations.SerializedName

interface ListCreatorsResponse : DomainMappable<ListCreatorsResult> {
    override fun asDomain(): ListCreatorsResult
}

data class SucceedResponse(
        @SerializedName("code") val code: String,
        @SerializedName("status") val status: String,
        @SerializedName("copyright") val copyright: String,
        @SerializedName("etag") val eTag: String,
        @SerializedName("data") val data: DataCreatorResponse
) : ListCreatorsResponse {

    override fun asDomain(): ListCreatorsResult {
        val results = data.results.map { creator ->
            val thumbnail = Thumbnail(creator.thumbnail.getPathWithExtension())
           Creator(
                   creator.id,
                   creator.firstName,
                   creator.middleName,
                   creator.lastName,
                   creator.fullName,
                   creator.modified,
                   thumbnail
           )
        }
        val data = DataCreatorResult(data.offset, data.limit, data.total, data.count, results)
        return CreatorSucceedResult(code, status, copyright, eTag, data)
    }
}

data class DataCreatorResponse(
        @SerializedName("offset") val offset: Int,
        @SerializedName("limit") val limit: Int,
        @SerializedName("total") val total: Int,
        @SerializedName("count") val count: Int,
        @SerializedName("results") val results: List<CreatorResponse>
)

data class CreatorResponse(
        @SerializedName("id") val id: Int,
        @SerializedName("firstName") val firstName: String,
        @SerializedName("middleName") val middleName: String,
        @SerializedName("lastName") val lastName: String,
        @SerializedName("fullName") val fullName: String,
        @SerializedName("modified") val modified: String,
        @SerializedName("thumbnail") val thumbnail: ThumbnailResponse
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
) : ListCreatorsResponse {

    override fun asDomain() = CreatorFailedResult(code, status)
}