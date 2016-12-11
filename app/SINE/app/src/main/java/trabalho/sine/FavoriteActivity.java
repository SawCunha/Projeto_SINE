package trabalho.sine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import trabalho.sine.adapter.AdapterListView;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.enun.Filtro;
import trabalho.sine.model.Vaga;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdapterListView mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RadioButton semfiltro, ultimasvagas, maiorsalario;
    private List<Vaga> vagas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_empregos_favoritos);
        semfiltro = (RadioButton) findViewById(R.id.semfiltro);
        ultimasvagas = (RadioButton) findViewById(R.id.ultimasvagas);
        maiorsalario = (RadioButton) findViewById(R.id.maiorsalario);

        verficaFiltroSelecionado();
    }

    private void verficaFiltroSelecionado() {
        if(semfiltro.isChecked()) obtemVagasBanco(Filtro.SEM_FITRO);
        else if(maiorsalario.isChecked()) obtemVagasBanco(Filtro.MAIOR_SALARIO);
        else obtemVagasBanco(Filtro.ULTIMAS_VAGAS);

        createRecyclerView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        createRecyclerView();
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
    }

    //Obtem as Vagas salvas no Banco de Dados.
    public void obtemVagasBanco(Filtro filtro){
        List<Vaga> vgs = new ArrayList<>();

        VagaDAO dao = new VagaDAO(getApplicationContext());

        switch (filtro){
            case SEM_FITRO:
                vgs = dao.getAll();
                break;
            case MAIOR_SALARIO:
                vgs = dao.getAllOrderBy();
                break;
            case ULTIMAS_VAGAS:
                vgs = dao.getAll();
                break;
            default:
                break;
        }

        //Caso não houver vaga, informa ao usuario com um toast
        if(vgs.isEmpty()) Toast.makeText(this,"Você ainda não possui vagas favoritas.",Toast.LENGTH_LONG).show();

        this.vagas = vgs;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.semfiltro:
                if (checked) {
                    checkedUpdate(true, false, false);
                    obtemVagasBanco(Filtro.SEM_FITRO);
                    createRecyclerView();
                }
                break;
            case R.id.maiorsalario:
                if (checked){
                    checkedUpdate(false, false, true);
                    obtemVagasBanco(Filtro.MAIOR_SALARIO);
                    createRecyclerView();
                }
                break;
            case R.id.ultimasvagas:
                if (checked){
                    checkedUpdate(false, true, false);
                    obtemVagasBanco(Filtro.ULTIMAS_VAGAS);
                    createRecyclerView();
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
