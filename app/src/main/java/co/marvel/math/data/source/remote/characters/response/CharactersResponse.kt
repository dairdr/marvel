package co.marvel.math.data.source.remote.characters.response

import co.marvel.math.data.source.remote.DomainMappable
import com.google.gson.annotations.SerializedName

interface ListCharactersResponse : DomainMappable<ListCharactersResult> {
    override fun asDomain(): ListCharactersResult
}

data class SucceedResponse(
        @SerializedName("code") val code: String,
        @SerializedName("status") val status: String,
        @SerializedName("copyright") val copyright: String,
        @SerializedName("etag") val eTag: String,
        @SerializedName("data") val data: DataCharacterResponse
) : ListCharactersResponse {

    override fun asDomain(): ListCharactersResult {
        val results = data.results.map { character ->
            val thumbnail = Thumbnail(character.thumbnail.getPathWithExtension())
           Character(
                   character.id,
                   character.name,
                   character.description,
                   character.modified,
                   thumbnail
           )
        }
        val data = DataCharacterResult(data.offset, data.limit, data.total, data.count, results)
        return CharacterSucceedResult(code, status, copyright, eTag, data)
    }
}

data class DataCharacterResponse(
        @SerializedName("offset") val offset: Int,
        @SerializedName("limit") val limit: Int,
        @SerializedName("total") val total: Int,
        @SerializedName("count") val count: Int,
        @SerializedName("results") val results: List<CharacterResponse>
)

data class CharacterResponse(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("description") val description: String,
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
) : ListCharactersResponse {

    override fun asDomain() = CharacterFailedResult(code, status)
}