package co.marvel.math.data.source.remote.comics;

import co.marvel.math.data.source.remote.comics.response.SucceedResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ComicApi {

    @GET("v1/public/comics")
    Single<SucceedResponse> comics(
            @Query("ts") String timestamp,
            @Query("apikey") String apiKey,
            @Query("hash") String hash,
            @Query("limit") Integer limit
    );

    @GET("v1/public/comics/{id}")
    Single<SucceedResponse> comic(
            @Path("id") Integer id,
            @Query("ts") String timestamp,
            @Query("apikey") String apiKey,
            @Query("hash") String hash
    );
}
