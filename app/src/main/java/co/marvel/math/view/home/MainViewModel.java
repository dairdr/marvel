package co.marvel.math.view.home;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import co.marvel.math.BuildConfig;
import co.marvel.math.data.source.CharactersDataSource;
import co.marvel.math.data.source.ComicsDataSource;
import co.marvel.math.data.source.CreatorsDataSource;
import co.marvel.math.data.source.EventsDataSource;
import co.marvel.math.data.source.SeriesDataSource;
import co.marvel.math.data.source.StoriesDataSource;
import co.marvel.math.data.source.remote.characters.response.Character;
import co.marvel.math.data.source.remote.characters.response.CharacterSucceedResult;
import co.marvel.math.data.source.remote.characters.response.ListCharactersResult;
import co.marvel.math.data.source.remote.comics.response.Comic;
import co.marvel.math.data.source.remote.comics.response.ComicSucceedResult;
import co.marvel.math.data.source.remote.comics.response.ListComicsResult;
import co.marvel.math.data.source.remote.creators.response.Creator;
import co.marvel.math.data.source.remote.creators.response.CreatorSucceedResult;
import co.marvel.math.data.source.remote.creators.response.ListCreatorsResult;
import co.marvel.math.data.source.remote.events.response.Event;
import co.marvel.math.data.source.remote.events.response.EventSucceedResult;
import co.marvel.math.data.source.remote.events.response.ListEventsResult;
import co.marvel.math.data.source.remote.series.response.ListSeriesResult;
import co.marvel.math.data.source.remote.series.response.Serie;
import co.marvel.math.data.source.remote.series.response.SerieSucceedResult;
import co.marvel.math.data.source.remote.stories.response.ListStoriesResult;
import co.marvel.math.data.source.remote.stories.response.Story;
import co.marvel.math.data.source.remote.stories.response.StorySucceedResult;
import co.marvel.math.utils.Expression;
import co.marvel.math.utils.StringExt;
import co.marvel.math.view.list.adapter.CharacterOption;
import co.marvel.math.view.list.adapter.ComicOption;
import co.marvel.math.view.list.adapter.CreatorOption;
import co.marvel.math.view.list.adapter.EventOption;
import co.marvel.math.view.list.adapter.ResultOption;
import co.marvel.math.view.list.adapter.SerieOption;
import co.marvel.math.view.list.adapter.StoryOption;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

enum DestinyList {
    CHARACTER,
    COMIC,
    CREATOR,
    EVENT,
    SERIE,
    STORY
}

public class MainViewModel extends ViewModel {
    // Data sources
    private CharactersDataSource charactersDataSource;
    private ComicsDataSource comicsDataSource;
    private CreatorsDataSource creatorsDataSource;
    private EventsDataSource eventsDataSource;
    private SeriesDataSource seriesDataSource;
    private StoriesDataSource storiesDataSource;

    // Live data
    private MutableLiveData<List<ResultOption>> _results = new MutableLiveData<>();
    public LiveData<List<ResultOption>> results = _results;

    private MutableLiveData<List<ResultOption>> _resultsFiltered = new MutableLiveData<>();
    public LiveData<List<ResultOption>> resultsFiltered = _resultsFiltered;

    private MutableLiveData<ResultOption> _result = new MutableLiveData<>();
    public LiveData<ResultOption> result = _result;

    private MutableLiveData<Integer> _output = new MutableLiveData<>();
    LiveData<Integer> output = _output;

    private MutableLiveData<Boolean> _busy = new MutableLiveData<>();
    public LiveData<Boolean> busy = _busy;

    private Disposable disposableRepository;

    public MainViewModel(
            CharactersDataSource charactersDataSource,
            ComicsDataSource comicsDataSource,
            CreatorsDataSource creatorsDataSource,
            EventsDataSource eventsDataSource,
            SeriesDataSource seriesDataSource,
            StoriesDataSource storiesDataSource
    ) {
        this.charactersDataSource = charactersDataSource;
        this.comicsDataSource = comicsDataSource;
        this.creatorsDataSource = creatorsDataSource;
        this.eventsDataSource = eventsDataSource;
        this.seriesDataSource = seriesDataSource;
        this.storiesDataSource = storiesDataSource;
    }

    private String buildTimestamp() {
        return String.format(Locale.getDefault(), "%d", System.currentTimeMillis());
    }

    private String buildHash(String timestamp) {
        String unencryptedHash = String.format(
                Locale.getDefault(),
                "%s%s%s",
                timestamp,
                BuildConfig.API_SECRET,
                BuildConfig.API_KEY
        );
        return StringExt.md5(unencryptedHash);
    }

    private List<ResultOption> buildCharactersList(CharacterSucceedResult result) {
        ArrayList<ResultOption> list = new ArrayList<>();
        for (Character item : result.getData().getResults()) {
            list.add(
                    new CharacterOption(
                            String.format(Locale.getDefault(), "%d", item.getId()),
                            item.getName(),
                            item.getDescription(),
                            item.getThumbnail().getUrl()
                    )
            );
        }
        return list;
    }

    private List<ResultOption> buildComicsList(ComicSucceedResult result) {
        ArrayList<ResultOption> list = new ArrayList<>();
        for (Comic item : result.getData().getResults()) {
            list.add(
                    new ComicOption(
                            String.format(Locale.getDefault(), "%d", item.getId()),
                            item.getTitle(),
                            item.getDescription() != null ? item.getDescription() : "",
                            item.getFormat(),
                            item.getThumbnail().getUrl()
                    )
            );
        }
        return list;
    }

    private List<ResultOption> buildCreatorsList(CreatorSucceedResult result) {
        ArrayList<ResultOption> list = new ArrayList<>();
        for (Creator item : result.getData().getResults()) {
            list.add(
                    new CreatorOption(
                            String.format(Locale.getDefault(), "%d", item.getId()),
                            item.getFirstName(),
                            item.getMiddleName(),
                            item.getLastName(),
                            item.getFullName(),
                            item.getThumbnail().getUrl()
                    )
            );
        }
        return list;
    }

    private List<ResultOption> buildEventsList(EventSucceedResult result) {
        ArrayList<ResultOption> list = new ArrayList<>();
        for (Event item : result.getData().getResults()) {
            list.add(
                    new EventOption(
                            String.format(Locale.getDefault(), "%d", item.getId()),
                            item.getTitle(),
                            item.getDescription(),
                            item.getThumbnail().getUrl(),
                            item.getStart(),
                            item.getEnd()
                    )
            );
        }
        return list;
    }

    private List<ResultOption> buildSeriesList(SerieSucceedResult result) {
        ArrayList<ResultOption> list = new ArrayList<>();
        for (Serie item : result.getData().getResults()) {
            list.add(
                    new SerieOption(
                            String.format(Locale.getDefault(), "%d", item.getId()),
                            item.getTitle(),
                            item.getDescription() != null ? item.getDescription() : "",
                            item.getThumbnail().getUrl(),
                            item.getStartYear(),
                            item.getEndYear(),
                            item.getRating()
                    )
            );
        }
        return list;
    }

    private List<ResultOption> buildStoriesList(StorySucceedResult result) {
        ArrayList<ResultOption> list = new ArrayList<>();
        for (Story item : result.getData().getResults()) {
            list.add(
                    new StoryOption(
                            String.format(Locale.getDefault(), "%d", item.getId()),
                            item.getTitle(),
                            item.getDescription(),
                            item.getThumbnail() != null ? item.getThumbnail().getUrl() : null
                    )
            );
        }
        return list;
    }

    private void resetFilter() {
        _resultsFiltered.setValue(_results.getValue());
    }

    public void fetchCharacters() {
        _busy.setValue(true);
        String timestamp = buildTimestamp();
        String hash = buildHash(timestamp);

        disposableRepository = charactersDataSource.characters(timestamp, BuildConfig.API_KEY, hash, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListCharactersResult>() {
                    @Override
                    public void accept(ListCharactersResult listCharactersResult) throws Exception {
                        _busy.setValue(false);
                        if (listCharactersResult instanceof CharacterSucceedResult) {
                            CharacterSucceedResult result = (CharacterSucceedResult) listCharactersResult;
                            _results.setValue(buildCharactersList(result));
                            _resultsFiltered.setValue(_results.getValue());
                        } else {
                            _results.setValue(null);
                            _resultsFiltered.setValue(null);
                        }
                    }
                });
    }

    public void fetchSingleCharacter(Integer id) {
        _busy.setValue(true);
        String timestamp = buildTimestamp();
        String hash = buildHash(timestamp);

        disposableRepository = charactersDataSource.character(id, timestamp, BuildConfig.API_KEY, hash)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListCharactersResult>() {
                    @Override
                    public void accept(ListCharactersResult listCharactersResult) throws Exception {
                        _busy.setValue(false);
                        if (listCharactersResult instanceof CharacterSucceedResult) {
                            CharacterSucceedResult result = (CharacterSucceedResult) listCharactersResult;
                            List<ResultOption> list = buildCharactersList(result);
                            if (!list.isEmpty()) {
                                _result.setValue(list.get(0));
                            } else {
                                _result.setValue(null);
                            }
                        } else {
                            _result.setValue(null);
                        }
                    }
                });
    }

    public void fetchComics() {
        _busy.setValue(true);
        String timestamp = buildTimestamp();
        String hash = buildHash(timestamp);

        disposableRepository = comicsDataSource.comics(timestamp, BuildConfig.API_KEY, hash, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListComicsResult>() {
                    @Override
                    public void accept(ListComicsResult listComicsResult) throws Exception {
                        _busy.setValue(false);
                        if (listComicsResult instanceof ComicSucceedResult) {
                            ComicSucceedResult result = (ComicSucceedResult) listComicsResult;
                            _results.setValue(buildComicsList(result));
                            _resultsFiltered.setValue(_results.getValue());
                        } else {
                            _results.setValue(null);
                            _resultsFiltered.setValue(null);
                        }
                    }
                });
    }

    public void fetchSingleComic(Integer id) {
        _busy.setValue(true);
        String timestamp = buildTimestamp();
        String hash = buildHash(timestamp);

        disposableRepository = comicsDataSource.comic(id, timestamp, BuildConfig.API_KEY, hash)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListComicsResult>() {
                    @Override
                    public void accept(ListComicsResult listComicsResult) throws Exception {
                        _busy.setValue(false);
                        if (listComicsResult instanceof ComicSucceedResult) {
                            ComicSucceedResult result = (ComicSucceedResult) listComicsResult;
                            List<ResultOption> list = buildComicsList(result);
                            if (!list.isEmpty()) {
                                _result.setValue(list.get(0));
                            } else {
                                _result.setValue(null);
                            }
                        } else {
                            _result.setValue(null);
                        }
                    }
                });
    }

    public void fetchCreators() {
        _busy.setValue(true);
        String timestamp = buildTimestamp();
        String hash = buildHash(timestamp);

        disposableRepository = creatorsDataSource.creators(timestamp, BuildConfig.API_KEY, hash, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListCreatorsResult>() {
                    @Override
                    public void accept(ListCreatorsResult listCreatorsResult) throws Exception {
                        _busy.setValue(false);
                        if (listCreatorsResult instanceof CreatorSucceedResult) {
                            CreatorSucceedResult result = (CreatorSucceedResult) listCreatorsResult;
                            _results.setValue(buildCreatorsList(result));
                            _resultsFiltered.setValue(_results.getValue());
                        } else {
                            _results.setValue(null);
                            _resultsFiltered.setValue(null);
                        }
                    }
                });
    }

    public void fetchSingleCreator(Integer id) {
        _busy.setValue(true);
        String timestamp = buildTimestamp();
        String hash = buildHash(timestamp);

        disposableRepository = creatorsDataSource.creator(id, timestamp, BuildConfig.API_KEY, hash)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListCreatorsResult>() {
                    @Override
                    public void accept(ListCreatorsResult listComicsResult) throws Exception {
                        _busy.setValue(false);
                        if (listComicsResult instanceof CreatorSucceedResult) {
                            CreatorSucceedResult result = (CreatorSucceedResult) listComicsResult;
                            List<ResultOption> list = buildCreatorsList(result);
                            if (!list.isEmpty()) {
                                _result.setValue(list.get(0));
                            } else {
                                _result.setValue(null);
                            }
                        } else {
                            _result.setValue(null);
                        }
                    }
                });
    }

    public void fetchEvents() {
        _busy.setValue(true);
        String timestamp = buildTimestamp();
        String hash = buildHash(timestamp);

        disposableRepository = eventsDataSource.events(timestamp, BuildConfig.API_KEY, hash, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListEventsResult>() {
                    @Override
                    public void accept(ListEventsResult listEventsResult) throws Exception {
                        _busy.setValue(false);
                        if (listEventsResult instanceof EventSucceedResult) {
                            EventSucceedResult result = (EventSucceedResult) listEventsResult;
                            _results.setValue(buildEventsList(result));
                            _resultsFiltered.setValue(_results.getValue());
                        } else {
                            _results.setValue(null);
                            _resultsFiltered.setValue(null);
                        }
                    }
                });
    }

    public void fetchSingleEvent(Integer id) {
        _busy.setValue(true);
        String timestamp = buildTimestamp();
        String hash = buildHash(timestamp);

        disposableRepository = eventsDataSource.event(id, timestamp, BuildConfig.API_KEY, hash)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListEventsResult>() {
                    @Override
                    public void accept(ListEventsResult listEventsResult) throws Exception {
                        _busy.setValue(false);
                        if (listEventsResult instanceof EventSucceedResult) {
                            EventSucceedResult result = (EventSucceedResult) listEventsResult;
                            List<ResultOption> list = buildEventsList(result);
                            if (!list.isEmpty()) {
                                _result.setValue(list.get(0));
                            } else {
                                _result.setValue(null);
                            }
                        } else {
                            _result.setValue(null);
                        }
                    }
                });
    }

    public void fetchSeries() {
        _busy.setValue(true);
        String timestamp = buildTimestamp();
        String hash = buildHash(timestamp);

        disposableRepository = seriesDataSource.series(timestamp, BuildConfig.API_KEY, hash, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListSeriesResult>() {
                    @Override
                    public void accept(ListSeriesResult listSeriesResult) throws Exception {
                        _busy.setValue(false);
                        if (listSeriesResult instanceof SerieSucceedResult) {
                            SerieSucceedResult result = (SerieSucceedResult) listSeriesResult;
                            _results.setValue(buildSeriesList(result));
                            _resultsFiltered.setValue(_results.getValue());
                        } else {
                            _results.setValue(null);
                            _resultsFiltered.setValue(null);
                        }
                    }
                });
    }

    public void fetchSingleSerie(Integer id) {
        _busy.setValue(true);
        String timestamp = buildTimestamp();
        String hash = buildHash(timestamp);

        disposableRepository = seriesDataSource.serie(id, timestamp, BuildConfig.API_KEY, hash)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListSeriesResult>() {
                    @Override
                    public void accept(ListSeriesResult listSeriesResult) throws Exception {
                        _busy.setValue(false);
                        if (listSeriesResult instanceof SerieSucceedResult) {
                            SerieSucceedResult result = (SerieSucceedResult) listSeriesResult;
                            List<ResultOption> list = buildSeriesList(result);
                            if (!list.isEmpty()) {
                                _result.setValue(list.get(0));
                            } else {
                                _result.setValue(null);
                            }
                        } else {
                            _result.setValue(null);
                        }
                    }
                });
    }

    public void fetchStories() {
        _busy.setValue(true);
        String timestamp = buildTimestamp();
        String hash = buildHash(timestamp);

        disposableRepository = storiesDataSource.stories(timestamp, BuildConfig.API_KEY, hash, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListStoriesResult>() {
                    @Override
                    public void accept(ListStoriesResult listStoriesResult) throws Exception {
                        _busy.setValue(false);
                        if (listStoriesResult instanceof StorySucceedResult) {
                            StorySucceedResult result = (StorySucceedResult) listStoriesResult;
                            _results.setValue(buildStoriesList(result));
                            _resultsFiltered.setValue(_results.getValue());
                        } else {
                            _results.setValue(null);
                            _resultsFiltered.setValue(null);
                        }
                    }
                });
    }

    public void fetchSingleStory(Integer id) {
        _busy.setValue(true);
        String timestamp = buildTimestamp();
        String hash = buildHash(timestamp);

        disposableRepository = storiesDataSource.story(id, timestamp, BuildConfig.API_KEY, hash)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListStoriesResult>() {
                    @Override
                    public void accept(ListStoriesResult listStoriesResult) throws Exception {
                        _busy.setValue(false);
                        if (listStoriesResult instanceof StorySucceedResult) {
                            StorySucceedResult result = (StorySucceedResult) listStoriesResult;
                            List<ResultOption> list = buildStoriesList(result);
                            if (!list.isEmpty()) {
                                _result.setValue(list.get(0));
                            } else {
                                _result.setValue(null);
                            }
                        } else {
                            _result.setValue(null);
                        }
                    }
                });
    }

    public Boolean multipleOf(Integer value, Integer of) {
        return value % of == 0;
    }

    DestinyList determinateList(@Nullable Integer value) {
        if (value == null) {
            return DestinyList.STORY;
        } else if (value == 0) {
            return DestinyList.CHARACTER;
        } else if (multipleOf(value, 3) || multipleOf(value, 5)) {
            return DestinyList.COMIC;
        } else if (multipleOf(value, 7)) {
            return DestinyList.CREATOR;
        } else if (multipleOf(value, 11)) {
            return DestinyList.EVENT;
        } else if (multipleOf(value, 13)) {
            return DestinyList.SERIE;
        } else {
            return DestinyList.STORY;
        }
    }

    void calculate(String expression) {
        Expression exp = new Expression(expression);
        if (!exp.isInvalid(expression)) {
            _output.setValue(exp.calculate());
        } else {
            _output.setValue(null);
        }
    }

    public void filter(String criteria) {
        // TODO: this filter can be improved using functional programming or kotlin
        if (criteria.toCharArray().length > 0) {
            ArrayList<ResultOption> filtered = new ArrayList<>();
            if (_results.getValue() != null) {
                for (ResultOption option : _results.getValue()) {
                    if (option.getId().contains(criteria) ||
                            option.getName().toLowerCase().contains(criteria) ||
                            (
                                    option.getDescription() != null &&
                                            option.getDescription().toLowerCase().contains(criteria)
                            )) {
                        filtered.add(option);
                    }
                }
            }

            _resultsFiltered.setValue(filtered);
        } else {
            resetFilter();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (disposableRepository != null) {
            disposableRepository.dispose();
        }
    }
}
