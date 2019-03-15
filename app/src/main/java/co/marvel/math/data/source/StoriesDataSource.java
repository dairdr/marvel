package co.marvel.math.data.source;

import co.marvel.math.data.source.remote.stories.response.ListStoriesResult;
import io.reactivex.Single;

public interface StoriesDataSource {
    Single<ListStoriesResult> stories(
            String timestamp,
            String apiKey,
            String hash,
            Integer limit
    );

    Single<ListStoriesResult> story(
            Integer id,
            String timestamp,
            String apiKey,
            String hash
    );
}
