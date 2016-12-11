package trabalho.sine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchForGraphicActivity extends AppCompatActivity {

    @BindView(R.id.search_button) Button searchButton;
    @BindView(R.id.cargo_search_edit_text) EditText cargoSearchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_graphic);

        ButterKnife.bind(this);
        ButterKnife.setDebug(true);
    }
}
