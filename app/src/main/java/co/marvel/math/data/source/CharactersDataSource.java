package co.marvel.math.data.source;

import co.marvel.math.data.source.remote.characters.response.ListCharactersResult;
import io.reactivex.Single;

public interface CharactersDataSource {
    Single<ListCharactersResult> characters(
            String timestamp,
            String apiKey,
            String hash,
            Integer limit
    );

    Single<ListCharactersResult> character(
            Integer id,
            String timestamp,
            String apiKey,
            String hash
    );
}
