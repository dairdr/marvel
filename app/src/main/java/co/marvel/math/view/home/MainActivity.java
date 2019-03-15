package co.marvel.math.view.home;

import androidx.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import co.marvel.math.R;
import co.marvel.math.utils.AppCompactActivityExtKt;
import co.marvel.math.view.list.ListActivity;

public class MainActivity extends AppCompatActivity {
    // UI Components
    private EditText input;
    private Button doMath;
    private Button viewList;
    private TextView output;

    // Live data
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.editText);
        output = findViewById(R.id.output);
        doMath = findViewById(R.id.doMath);
        viewList = findViewById(R.id.viewList);

        viewModel = AppCompactActivityExtKt.buildViewModel(this, MainViewModel.class);
        viewModel.output.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer value) {
                if (value != null) {
                    output.setText(String.format(Locale.getDefault(), "%d", value));
                } else {
                    output.setText(String.format(Locale.getDefault(), "%s", getString(R.string.invalid)));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        doMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = input.getText().toString().trim();
                if (!value.isEmpty()) {
                    viewModel.calculate(value);
                }
            }
        });
        viewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                switch (viewModel.determinateList(viewModel.output.getValue())) {
                    case CHARACTER:
                        intent.putExtra(ListActivity.TYPE_KEY, ListActivity.CHARACTER_MODE);
                        break;
                    case COMIC:
                        intent.putExtra(ListActivity.TYPE_KEY, ListActivity.COMIC_MODE);
                        break;
                    case CREATOR:
                        intent.putExtra(ListActivity.TYPE_KEY, ListActivity.CREATOR_MODE);
                        break;
                    case EVENT:
                        intent.putExtra(ListActivity.TYPE_KEY, ListActivity.EVENT_MODE);
                        break;
                    case SERIE:
                        intent.putExtra(ListActivity.TYPE_KEY, ListActivity.SERIE_MODE);
                        break;
                    default:
                        intent.putExtra(ListActivity.TYPE_KEY, ListActivity.STORY_MODE);
                        break;
                }
                startActivity(intent);
            }
        });
    }
}
