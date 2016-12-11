package trabalho.sine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trabalho.sine.controller.RequestURL;

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

        //Falta implementar a lógica para buscar os dados

        RequestURL requestURL = new RequestURL(this);

        requestURL.requestURL("blablabla", new RequestURL.VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Intent graphicIntent = new Intent(SearchForGraphicActivity.this, GraphicActivity.class);
                startActivity(graphicIntent);
            }
        });


    }
}
