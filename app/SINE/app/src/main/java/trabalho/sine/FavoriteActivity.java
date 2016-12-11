package trabalho.sine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import trabalho.sine.adapter.AdapterListView;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.enun.Filtro;
import trabalho.sine.model.Vaga;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdapterListView mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RadioButton semfiltro, ultimasvagas,maiorsalario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_empregos_favoritos);
        semfiltro = (RadioButton) findViewById(R.id.semfiltro);
        ultimasvagas = (RadioButton) findViewById(R.id.ultimasvagas);
        maiorsalario = (RadioButton) findViewById(R.id.maiorsalario);

        createRecyclerView(Filtro.SEM_FITRO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        createRecyclerView(Filtro.SEM_FITRO);
    }

    private void createRecyclerView(Filtro filtro){
        //Remove os itens do Recycler, para add os novos valores.
        mRecyclerView.removeAllViewsInLayout();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterListView(obtemVagasBanco(filtro),this);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    //Obtem as Vagas salvas no Banco de Dados.
    public List<Vaga> obtemVagasBanco(Filtro filtro){
        List<Vaga> vagas = new ArrayList<>();

        VagaDAO dao = new VagaDAO(getApplicationContext());

        switch (filtro){
            case SEM_FITRO:
                vagas = dao.getAll();
                break;
            case MAIOR_SALARIO:
                vagas = dao.getAllOrderBy();
                break;
            case ULTIMAS_VAGAS:
                vagas = dao.getAll();
                break;
            default:
                vagas = dao.getAll();
                break;
        }

        //Caso não houver vaga, informa ao usuario com um toast
        if(vagas.isEmpty()) Toast.makeText(this,"Você ainda não possui vagas favoritas.",Toast.LENGTH_LONG).show();

        return vagas;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.semfiltro:
                if (checked) {
                    checkedUpdate(true, false, false);
                    createRecyclerView(Filtro.SEM_FITRO);
                }
                break;
            case R.id.maiorsalario:
                if (checked){
                    checkedUpdate(false, false, true);
                    createRecyclerView(Filtro.MAIOR_SALARIO);
                }
                break;
            case R.id.ultimasvagas:
                if (checked){
                    checkedUpdate(false, true, false);
                    createRecyclerView(Filtro.ULTIMAS_VAGAS);
                }
                break;
        }
    }

    private void checkedUpdate(Boolean s, Boolean u, Boolean m){
        semfiltro.setChecked(s);
        maiorsalario.setChecked(m);
        ultimasvagas.setChecked(u);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(2,returnIntent);
        super.onBackPressed();
    }

}
