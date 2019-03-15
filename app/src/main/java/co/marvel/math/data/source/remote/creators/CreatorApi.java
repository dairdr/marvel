package co.marvel.math.data.source.remote.creators;

import co.marvel.math.data.source.remote.creators.response.SucceedResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CreatorApi {

    @GET("v1/public/creators")
    Single<SucceedResponse> creators(
            @Query("ts") String timestamp,
            @Query("apikey") String apiKey,
            @Query("hash") String hash,
            @Query("limit") Integer limit
    );

    @GET("v1/public/creators/{id}")
    Single<SucceedResponse> creator(
            @Path("id") Integer id,
            @Query("ts") String timestamp,
            @Query("apikey") String apiKey,
            @Query("hash") String hash
    );
}
