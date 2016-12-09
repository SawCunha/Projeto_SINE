package trabalho.sine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;

import trabalho.sine.dao.VagaDAO;
import trabalho.sine.model.Vaga;

public class ResultActivity extends AppCompatActivity {


    private ImageButton favoriteBtn;
    private ImageButton shareBtn;
    private Vaga vaga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        favoriteBtn = (ImageButton) findViewById(R.id.favoritoBTN);

        carregaInforActivity(getIntent().getExtras());

    }

    private void carregaInforActivity(Bundle bundle) {
        String vagaJson = (String) bundle.get("vaga");

        Toast.makeText(this, vagaJson, Toast.LENGTH_LONG).show();

        vaga = new Gson().fromJson(vagaJson, Vaga.class);

        favoriteBtn.setBackgroundResource(
                (vaga.getId() == null ?
                        R.drawable.ic_favorite_border_black_48dp : R.drawable.ic_favorite_black));

    }

    public void favoriteClick(View view) {
        VagaDAO vagaDAO = new VagaDAO(this.getApplicationContext());
        if (vaga.getId() == null) {
            vagaDAO.insert(vaga);
            favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_black);
            Toast.makeText(this, "Favoritado!!!", Toast.LENGTH_SHORT).show();
        } else {
            vagaDAO.delete(vaga.getId());
            vaga.setId(null);
            favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_border_black_48dp);
            Toast.makeText(this, "Desfavoritado!!!", Toast.LENGTH_SHORT).show();
        }

    }
}
