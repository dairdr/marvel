package co.marvel.math.view.list;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.marvel.math.R;
import co.marvel.math.utils.AppCompactActivityExtKt;
import co.marvel.math.view.detail.DetailActivity;
import co.marvel.math.view.home.MainViewModel;
import co.marvel.math.view.list.adapter.ResultAdapter;
import co.marvel.math.view.list.adapter.ResultOption;
import co.marvel.math.view.list.adapter.ResultOptionListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class ListActivity extends AppCompatActivity implements ResultOptionListener {
    // UI components
    private ResultAdapter adapter;
    private ProgressBar loading;
    private EditText search;

    public static String TYPE_KEY = "type";
    public static String CHARACTER_MODE = "character";
    public static String COMIC_MODE = "comic";
    public static String CREATOR_MODE = "creator";
    public static String EVENT_MODE = "event";
    public static String SERIE_MODE = "serie";
    public static String STORY_MODE = "story";


    // Live data
    private MainViewModel viewModel;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        loading = findViewById(R.id.loading);
        search = findViewById(R.id.search);

        adapter = new ResultAdapter(getLayoutInflater(), this);

        viewModel = AppCompactActivityExtKt.buildViewModel(this, MainViewModel.class);
        viewModel.resultsFiltered.observe(this, new Observer<List<ResultOption>>() {
            @Override
            public void onChanged(@Nullable List<ResultOption> resultOptions) {
                if (resultOptions != null) {
                    adapter.setData(resultOptions);
                } else {
                    Toast.makeText(ListActivity.this, R.string.noResultsFound, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.busy.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null && !aBoolean) {
                    loading.setVisibility(View.GONE);
                } else {
                    loading.setVisibility(View.VISIBLE);
                }
            }
        });

        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        Drawable drawable = getDrawable(R.drawable.item_list_divider);
        if (drawable != null) {
            divider.setDrawable(drawable);
        }

        RecyclerView list = findViewById(R.id.results);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        list.addItemDecoration(divider);

        determinateFetch();
    }

    @Override
    protected void onStart() {
        super.onStart();

        disposable = RxTextView
                .textChangeEvents(search)
                .skipInitialValue()
                .debounce(300, TimeUnit.MILLISECONDS)
                .map(new Function<TextViewTextChangeEvent, String>() {
                    @Override
                    public String apply(TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
                        return textViewTextChangeEvent.getView().getText().toString();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        RecyclerView list = findViewById(R.id.results);
                        list.scrollToPosition(0);
                        viewModel.filter(s);
                    }
                });

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public void onClickCharacter(@NotNull String id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.ID_KEY, id);
        intent.putExtra(DetailActivity.TYPE_KEY, DetailActivity.CHARACTER_MODE);
        startActivity(intent);
    }

    @Override
    public void onClickComic(@NotNull String id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.ID_KEY, id);
        intent.putExtra(DetailActivity.TYPE_KEY, DetailActivity.COMIC_MODE);
        startActivity(intent);
    }

    @Override
    public void onClickCreator(@NotNull String id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.ID_KEY, id);
        intent.putExtra(DetailActivity.TYPE_KEY, DetailActivity.CREATOR_MODE);
        startActivity(intent);
    }

    @Override
    public void onClickEvent(@NotNull String id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.ID_KEY, id);
        intent.putExtra(DetailActivity.TYPE_KEY, DetailActivity.EVENT_MODE);
        startActivity(intent);
    }

    @Override
    public void onClickSerie(@NotNull String id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.ID_KEY, id);
        intent.putExtra(DetailActivity.TYPE_KEY, DetailActivity.SERIE_MODE);
        startActivity(intent);
    }

    @Override
    public void onClickStory(@NotNull String id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.ID_KEY, id);
        intent.putExtra(DetailActivity.TYPE_KEY, DetailActivity.STORY_MODE);
        startActivity(intent);
    }

    private void determinateFetch() {
        if (getIntent().hasExtra(TYPE_KEY)) {
            String mode = getIntent().getStringExtra(TYPE_KEY);
            if (mode.equals(ListActivity.CHARACTER_MODE)) {
                setTitle(R.string.charactersTitle);
                viewModel.fetchCharacters();
            } else if (mode.equals(ListActivity.COMIC_MODE)) {
                setTitle(R.string.comicsTitle);
                viewModel.fetchComics();
            } else if (mode.equals(ListActivity.CREATOR_MODE)) {
                setTitle(R.string.creatorsTitle);
                viewModel.fetchCreators();
            } else if (mode.equals(ListActivity.EVENT_MODE)) {
                setTitle(R.string.eventsTitle);
                viewModel.fetchEvents();
            } else if (mode.equals(ListActivity.SERIE_MODE)) {
                setTitle(R.string.seriesTitle);
                viewModel.fetchSeries();
            } else if (mode.equals(ListActivity.STORY_MODE)) {
                setTitle(R.string.storiesTitle);
                viewModel.fetchStories();
            }
        }
    }
}
