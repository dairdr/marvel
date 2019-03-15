package co.marvel.math.data.source.remote.series;

import co.marvel.math.data.source.remote.series.response.SucceedResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SerieApi {

    @GET("v1/public/series")
    Single<SucceedResponse> series(
            @Query("ts") String timestamp,
            @Query("apikey") String apiKey,
            @Query("hash") String hash,
            @Query("limit") Integer limit
    );

    @GET("v1/public/series/{id}")
    Single<SucceedResponse> serie(
            @Path("id") Integer id,
            @Query("ts") String timestamp,
            @Query("apikey") String apiKey,
            @Query("hash") String hash
    );
}
