package co.marvel.math.view.detail;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import androidx.lifecycle.Observer;
import co.marvel.math.R;
import co.marvel.math.utils.AppCompactActivityExtKt;
import co.marvel.math.view.home.MainViewModel;
import co.marvel.math.view.list.adapter.ResultOption;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class DetailActivity extends AppCompatActivity {
    // UI components
    private ProgressBar loading;
    private TextView detailTitle;
    private TextView detailDescription;
    private ImageView detailImage;

    public static String ID_KEY = "id";
    public static String TYPE_KEY = "type";
    public static String CHARACTER_MODE = "character";
    public static String COMIC_MODE = "comic";
    public static String CREATOR_MODE = "creator";
    public static String EVENT_MODE = "event";
    public static String SERIE_MODE = "serie";
    public static String STORY_MODE = "story";

    // Live data
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        loading = findViewById(R.id.loading);
        detailTitle = findViewById(R.id.detailTitle);
        detailTitle.setText(R.string.loadingDashes);
        detailDescription = findViewById(R.id.detailDescription);
        detailDescription.setText(R.string.loadingDashes);
        detailImage = findViewById(R.id.detailImage);

        viewModel = AppCompactActivityExtKt.buildViewModel(this, MainViewModel.class);
        viewModel.result.observe(this, new Observer<ResultOption>() {
            @Override
            public void onChanged(ResultOption resultOption) {
                if (resultOption != null) {
                    showDetailInfo(resultOption);
                } else {
                    Toast.makeText(DetailActivity.this, R.string.notFound, Toast.LENGTH_SHORT).show();
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

        determinateFetch();
    }

    private void determinateFetch() {
        if (getIntent().hasExtra(TYPE_KEY) && getIntent().hasExtra(ID_KEY)) {
            String rawId = getIntent().getStringExtra(ID_KEY);
            Integer id = Integer.parseInt(rawId);
            String mode = getIntent().getStringExtra(TYPE_KEY);

            if (mode.equals(DetailActivity.CHARACTER_MODE)) {
                viewModel.fetchSingleCharacter(id);
            } else if (mode.equals(DetailActivity.COMIC_MODE)) {
                viewModel.fetchSingleComic(id);
            } else if (mode.equals(DetailActivity.CREATOR_MODE)) {
                viewModel.fetchSingleCreator(id);
            } else if (mode.equals(DetailActivity.EVENT_MODE)) {
                viewModel.fetchSingleEvent(id);
            } else if (mode.equals(DetailActivity.SERIE_MODE)) {
                viewModel.fetchSingleSerie(id);
            } else if (mode.equals(DetailActivity.STORY_MODE)) {
                viewModel.fetchSingleStory(id);
            }
        }
    }

    private void showDetailInfo(ResultOption result) {
        detailTitle.setText(result.getName());
        if (result.getDescription() != null && !result.getDescription().equals("")) {
            detailDescription.setText(result.getDescription());
        } else {
            detailDescription.setText(R.string.notSpecify);
        }
        Glide
                .with(this)
                .load(result.getThumbnail())
                .transition(withCrossFade())
                .into(detailImage);
    }
}
