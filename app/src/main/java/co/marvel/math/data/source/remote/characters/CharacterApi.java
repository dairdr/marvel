package co.marvel.math.data.source.remote.characters;

import co.marvel.math.data.source.remote.characters.response.SucceedResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CharacterApi {

    @GET("v1/public/characters")
    Single<SucceedResponse> characters(
            @Query("ts") String timestamp,
            @Query("apikey") String apiKey,
            @Query("hash") String hash,
            @Query("limit") Integer limit
    );

    @GET("v1/public/characters/{id}")
    Single<SucceedResponse> character(
            @Path("id") Integer id,
            @Query("ts") String timestamp,
            @Query("apikey") String apiKey,
            @Query("hash") String hash
    );
}
