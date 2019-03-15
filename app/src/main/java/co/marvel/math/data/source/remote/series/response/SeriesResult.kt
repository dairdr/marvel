package co.marvel.math.data.source.remote.series.response

interface ListSeriesResult

data class SerieSucceedResult(
        val code: String,
        val status: String,
        val copyright: String,
        val eTag: String,
        val data: DataSerieResult
): ListSeriesResult

data class DataSerieResult(
        val offset: Int,
        val limit: Int,
        val total: Int,
        val count: Int,
        val results: List<Serie>
)

data class Serie(
        val id: Int,
        val title: String,
        val description: String?,
        val modified: String,
        val thumbnail: Thumbnail,
        val startYear: Int,
        val endYear: Int,
        val rating: String
)

data class Thumbnail(val url: String)

data class SerieFailedResult(
        val code: String,
        val status: String
) : ListSeriesResult