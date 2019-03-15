package co.marvel.math.data.source;

import co.marvel.math.data.source.remote.creators.response.ListCreatorsResult;
import io.reactivex.Single;

public interface CreatorsDataSource {
    Single<ListCreatorsResult> creators(
            String timestamp,
            String apiKey,
            String hash,
            Integer limit
    );

    Single<ListCreatorsResult> creator(
            Integer id,
            String timestamp,
            String apiKey,
            String hash
    );
}
