package co.marvel.math.data.source.remote.comics.response

import co.marvel.math.data.source.remote.DomainMappable
import com.google.gson.annotations.SerializedName

interface ListComicsResponse : DomainMappable<ListComicsResult> {
    override fun asDomain(): ListComicsResult
}

data class SucceedResponse(
        @SerializedName("code") val code: String,
        @SerializedName("status") val status: String,
        @SerializedName("copyright") val copyright: String,
        @SerializedName("etag") val eTag: String,
        @SerializedName("data") val data: DataComicResponse
) : ListComicsResponse {

    override fun asDomain(): ListComicsResult {
        val results = data.results.map { comic ->
            val thumbnail = Thumbnail(comic.thumbnail.getPathWithExtension())
           Comic(
                   comic.id,
                   comic.title,
                   comic.issueNumber,
                   comic.description,
                   comic.modified,
                   comic.format,
                   comic.pageCount,
                   thumbnail
           )
        }
        val data = DataComicResult(data.offset, data.limit, data.total, data.count, results)
        return ComicSucceedResult(code, status, copyright, eTag, data)
    }
}

data class DataComicResponse(
        @SerializedName("offset") val offset: Int,
        @SerializedName("limit") val limit: Int,
        @SerializedName("total") val total: Int,
        @SerializedName("count") val count: Int,
        @SerializedName("results") val results: List<ComicResponse>
)

data class ComicResponse(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("issueNumber") val issueNumber: Int,
        @SerializedName("variantDescription") val description: String?,
        @SerializedName("modified") val modified: String,
        @SerializedName("format") val format: String,
        @SerializedName("pageCount") val pageCount: Int,
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
) : ListComicsResponse {

    override fun asDomain() = ComicFailedResult(code, status)
}