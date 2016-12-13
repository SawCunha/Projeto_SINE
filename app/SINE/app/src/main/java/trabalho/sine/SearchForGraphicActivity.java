package trabalho.sine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchForGraphicActivity extends AppCompatActivity {

    @BindView(R.id.cargo_search_edit_text) EditText cargoSearchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_graphic);

        ButterKnife.bind(this);
        ButterKnife.setDebug(true);
    }

    @OnClick(R.id.search_button)
    public void pesquisarCargo(){

        Toast.makeText(this, "Teste ButterKniffe", Toast.LENGTH_SHORT).show();

    }
}
