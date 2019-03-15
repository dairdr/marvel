package co.marvel.math.data.source.remote.characters.response

interface ListCharactersResult

data class CharacterSucceedResult(
        val code: String,
        val status: String,
        val copyright: String,
        val eTag: String,
        val data: DataCharacterResult
): ListCharactersResult

data class DataCharacterResult(
        val offset: Int,
        val limit: Int,
        val total: Int,
        val count: Int,
        val results: List<Character>
)

data class Character(
        val id: Int,
        val name: String,
        val description: String,
        val modified: String,
        val thumbnail: Thumbnail
)

data class Thumbnail(val url: String)

data class CharacterFailedResult(
        val code: String,
        val status: String
) : ListCharactersResult