package co.marvel.math.data.source.remote.series.response

import co.marvel.math.data.source.remote.DomainMappable
import com.google.gson.annotations.SerializedName

interface ListSeriesResponse : DomainMappable<ListSeriesResult> {
    override fun asDomain(): ListSeriesResult
}

data class SucceedResponse(
        @SerializedName("code") val code: String,
        @SerializedName("status") val status: String,
        @SerializedName("copyright") val copyright: String,
        @SerializedName("etag") val eTag: String,
        @SerializedName("data") val data: DataSerieResponse
) : ListSeriesResponse {

    override fun asDomain(): ListSeriesResult {
        val results = data.results.map { serie ->
            val thumbnail = Thumbnail(serie.thumbnail.getPathWithExtension())
           Serie(
                   serie.id,
                   serie.title,
                   serie.description,
                   serie.modified,
                   thumbnail,
                   serie.start,
                   serie.end,
                   serie.rating
           )
        }
        val data = DataSerieResult(data.offset, data.limit, data.total, data.count, results)
        return SerieSucceedResult(code, status, copyright, eTag, data)
    }
}

data class DataSerieResponse(
        @SerializedName("offset") val offset: Int,
        @SerializedName("limit") val limit: Int,
        @SerializedName("total") val total: Int,
        @SerializedName("count") val count: Int,
        @SerializedName("results") val results: List<SerieResponse>
)

data class SerieResponse(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String?,
        @SerializedName("modified") val modified: String,
        @SerializedName("thumbnail") val thumbnail: ThumbnailResponse,
        @SerializedName("startYear") val start: Int,
        @SerializedName("endYear") val end: Int,
        @SerializedName("rating") val rating: String
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
) : ListSeriesResponse {

    override fun asDomain() = SerieFailedResult(code, status)
}