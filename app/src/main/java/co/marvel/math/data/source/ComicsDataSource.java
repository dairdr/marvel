package co.marvel.math.data.source;

import co.marvel.math.data.source.remote.comics.response.ListComicsResult;
import io.reactivex.Single;

public interface ComicsDataSource {
    Single<ListComicsResult> comics(
            String timestamp,
            String apiKey,
            String hash,
            Integer limit
    );

    Single<ListComicsResult> comic(
            Integer id,
            String timestamp,
            String apiKey,
            String hash
    );
}
