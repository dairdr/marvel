package co.marvel.math.data.source;

import co.marvel.math.data.source.remote.series.response.ListSeriesResult;
import io.reactivex.Single;

public interface SeriesDataSource {
    Single<ListSeriesResult> series(
            String timestamp,
            String apiKey,
            String hash,
            Integer limit
    );

    Single<ListSeriesResult> serie(
            Integer id,
            String timestamp,
            String apiKey,
            String hash
    );
}
