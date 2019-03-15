package co.marvel.math.data.source.remote.stories;

import co.marvel.math.data.source.remote.stories.response.SucceedResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StoryApi {

    @GET("v1/public/stories")
    Single<SucceedResponse> stories(
            @Query("ts") String timestamp,
            @Query("apikey") String apiKey,
            @Query("hash") String hash,
            @Query("limit") Integer limit
    );

    @GET("v1/public/stories/{id}")
    Single<SucceedResponse> story(
            @Path("id") Integer id,
            @Query("ts") String timestamp,
            @Query("apikey") String apiKey,
            @Query("hash") String hash
    );
}
