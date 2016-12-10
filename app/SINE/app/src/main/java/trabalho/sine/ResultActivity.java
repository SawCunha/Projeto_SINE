package trabalho.sine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import trabalho.sine.dao.VagaDAO;
import trabalho.sine.model.Vaga;

public class ResultActivity extends AppCompatActivity {


    private ImageButton favoriteBtn;
    private ImageButton shareBtn;
    private Vaga vaga;

    private TextView title, money, city, address, company, function, des, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        favoriteBtn = (ImageButton) findViewById(R.id.favoritoBTN);
        shareBtn = (ImageButton) findViewById(R.id.shareBTN);

        // Carregando os labels
        title = (TextView) findViewById(R.id.titleValue);
        money = (TextView) findViewById(R.id.moneyValue);
        city = (TextView) findViewById(R.id.cityValue);
        address = (TextView) findViewById(R.id.addressValue);
        company = (TextView) findViewById(R.id.companyValue);
        function = (TextView) findViewById(R.id.functionValue);
        des = (TextView) findViewById(R.id.descriptionValue);
        url = (TextView) findViewById(R.id.urlValue);


        carregaInforActivity(getIntent().getExtras());

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        //
        Toast.makeText(this,"To aki no voltar",Toast.LENGTH_SHORT).show();
        String vagaJson = new Gson().toJson(vaga);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("resultado",vagaJson);
        setResult(RESULT_OK,returnIntent);
        super.onBackPressed();
    }

    private void carregaInforActivity(Bundle bundle) {
        String vagaJson = (String) bundle.get("vaga");

        //Toast.makeText(this, vagaJson, Toast.LENGTH_LONG).show();

        vaga = new Gson().fromJson(vagaJson, Vaga.class);

        favoriteBtn.setBackgroundResource(
                (vaga.getId() == null ?
                        R.drawable.ic_favorite_border_black_48dp : R.drawable.ic_favorite_black));

        title.setText(vaga.getTitulo());
        money.setText(vaga.getSalario().toString());
        city.setText(vaga.getCidade());
        address.setText(vaga.getEndereco());
        company.setText(vaga.getEmpresa());
        function.setText(vaga.getFuncao());
        des.setText(vaga.getDescricao());
        url.setText(vaga.getUrlSine());

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

    // Compartilha o link do card relacionado a vaga.
    public void shareClick(View view){

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        share.putExtra(Intent.EXTRA_SUBJECT, "Teste");
        share.putExtra(Intent.EXTRA_TEXT, "http://ibertphilos.hol.es");

        startActivity(Intent.createChooser(share, "Compartilhar"));

    }
}
