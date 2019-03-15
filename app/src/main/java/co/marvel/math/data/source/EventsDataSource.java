package co.marvel.math.data.source;

import co.marvel.math.data.source.remote.events.response.ListEventsResult;
import io.reactivex.Single;

public interface EventsDataSource {
    Single<ListEventsResult> events(
            String timestamp,
            String apiKey,
            String hash,
            Integer limit
    );

    Single<ListEventsResult> event(
            Integer id,
            String timestamp,
            String apiKey,
            String hash
    );
}
