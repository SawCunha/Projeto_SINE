package trabalho.sine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.List;

import trabalho.sine.adapter.AdapterListView;
import trabalho.sine.controller.RequestURL;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.model.VagasJSON;
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

        obtemVagasAPI();

    }

    private void carregaRecyclerView() {
        verifica();
        createRecyclerView();
        dialog.dismiss();
    }

    private void createRecyclerView(){
        //Remove os itens do Recycler, para add os novos valores.
        mRecyclerView.removeAllViewsInLayout();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterListView(vagas,this);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutFrozen(false);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        verifica();
        createRecyclerView();
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

        // Se for true, quer dizer que nao há favoritos e por isso todas as vagas devem ser desmarcadas.
        if(vagasBd.isEmpty()) for(Vaga v : vagas) v.setFavoritado(false);

        for (Vaga vbd : vagasBd)
            for (Vaga vs : vagas)
                if (vbd.getId().toString().equalsIgnoreCase(vs.getId().toString())) vs.setFavoritado(true);
    }

    public void obtemVagasAPI(){
        RequestURL req = new RequestURL(this);
        //Testa a requisição.
        req.requestURL("http://192.168.0.106:10555/vagas", new RequestURL.VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                VagasJSON vagasJSON = gson.fromJson(response, VagasJSON.class);
                vagas = vagasJSON.getVagas();
                carregaRecyclerView();
            }
        });

    }


}