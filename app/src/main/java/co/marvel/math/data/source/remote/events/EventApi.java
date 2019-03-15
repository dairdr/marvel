package co.marvel.math.data.source.remote.events;

import co.marvel.math.data.source.remote.events.response.SucceedResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EventApi {

    @GET("v1/public/events")
    Single<SucceedResponse> events(
            @Query("ts") String timestamp,
            @Query("apikey") String apiKey,
            @Query("hash") String hash,
            @Query("limit") Integer limit
    );

    @GET("v1/public/events/{id}")
    Single<SucceedResponse> event(
            @Path("id") Integer id,
            @Query("ts") String timestamp,
            @Query("apikey") String apiKey,
            @Query("hash") String hash
    );
}
