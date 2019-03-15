package co.marvel.math.data.source

import co.marvel.math.data.source.remote.characters.CharacterApi
import co.marvel.math.data.source.remote.characters.response.FailedResponse
import co.marvel.math.data.source.remote.characters.response.ListCharactersResponse
import co.marvel.math.data.source.remote.characters.response.ListCharactersResult
import co.marvel.math.utils.mapError
import co.marvel.math.utils.mapNetworkErrors
import co.marvel.math.utils.mapToDomain
import io.reactivex.Single
import retrofit2.Retrofit

class CharacterRepository(private val retrofit: Retrofit): CharactersDataSource {

    override fun characters(
            timestamp: String?,
            apiKey: String?,
            hash: String?,
            limit: Int?
    ): Single<ListCharactersResult> {
        val characterApi = retrofit.create(CharacterApi::class.java)
        return characterApi
                .characters(timestamp, apiKey, hash, 20)
                .mapError<FailedResponse, ListCharactersResponse>()
                .mapNetworkErrors()
                .mapToDomain()
    }

    override fun character(
            id: Int?,
            timestamp: String?,
            apiKey: String?,
            hash: String?
    ): Single<ListCharactersResult> {
        val characterApi = retrofit.create(CharacterApi::class.java)
        return characterApi
                .character(id, timestamp, apiKey, hash)
                .mapError<FailedResponse, ListCharactersResponse>()
                .mapNetworkErrors()
                .mapToDomain()
    }

    companion object {
        @Volatile
        private var instance: CharacterRepository? = null

        @JvmStatic
        fun default(retrofit: Retrofit): CharacterRepository =
                instance ?: synchronized(CharacterRepository::class.java) {

                    instance ?: CharacterRepository(retrofit).also {
                        instance = it
                    }
                }
    }
}