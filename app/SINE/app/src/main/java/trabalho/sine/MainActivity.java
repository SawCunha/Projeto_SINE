package trabalho.sine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import trabalho.sine.adapter.AdapterListView;
import trabalho.sine.controller.RequestURL;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.dao.VagasJSON;
import trabalho.sine.model.Vaga;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdapterListView mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button favorite;
    private List<Vaga>vagas;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Carregando dados");
        dialog.setIndeterminate(true);
        dialog.show();

        favorite = (Button) findViewById(R.id.favoriteButton);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_empregos);
        gera();
    }

    private void createRecyclerView(){

        // Não é legal, está deletando e criando o recycler view novamente.
        mRecyclerView.removeAllViewsInLayout();

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterListView(vagas,this);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutFrozen(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(this, "OnRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this, resultCode + " = " + RESULT_OK, Toast.LENGTH_SHORT).show();
        if(resultCode == RESULT_OK){
            String resultado = data.getExtras().getString("resultado");
            //Coloque no EditText
            Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
        }

        else if(resultCode == 2)
            Toast.makeText(this, "Voltou do favorite", Toast.LENGTH_SHORT).show();

        verifica();
        createRecyclerView();

    }

    //Método de teste...
    public void gera(){

        RequestURL req = new RequestURL(this);

        //Testa a requisição.
        req.requestURL("http://192.168.0.106:10555/vagas", new RequestURL.VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                VagasJSON vagasJSON = gson.fromJson(response, VagasJSON.class);
                vagas = vagasJSON.getVagas();
                verifica();
                createRecyclerView();
                dialog.dismiss();

                for (Vaga v : vagasJSON.getVagas()) Log.d("Vaga: ", v.getTitulo());
            }
        });


    }


    // Abre a intente de favoritos.
    public void favoriteOpenClick(View view) {

        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivityForResult(intent, 2);


    }

    // Verifica quais das vagas está no banco de dados.
    public void verifica() {

        VagaDAO vagaDAO = new VagaDAO(getApplicationContext());
        List<Vaga> vagasBd = vagaDAO.getAll();

        for (int contador = 0; contador < vagasBd.size(); contador++)
            for (int contador2 = 0; contador2 < vagas.size(); contador2++)
                if (vagasBd.get(contador).getId().toString().equalsIgnoreCase(vagas.get(contador2).getId().toString())) {
                    vagas.get(contador2).setFavoritado(true);
                    Log.d("darius", vagas.get(contador2).getId().toString());
                }
    }



    }
