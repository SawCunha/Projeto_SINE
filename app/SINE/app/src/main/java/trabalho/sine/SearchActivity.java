package trabalho.sine;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import trabalho.sine.adapter.AdapterListView;
import trabalho.sine.controller.RequestURL;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.enun.Filtro;
import trabalho.sine.model.Vaga;
import trabalho.sine.model.VagasJSON;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdapterListView mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button favorite;
    private List<Vaga> vagas;
    private ProgressDialog dialog;
    private SearchView search;
    private String filtroEscolhido = "";
    private int filtroIndex = 1;
    private AlertDialog alerta;
    private Button filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mostrarDialogoCarregando();

        favorite = (Button) findViewById(R.id.favoriteButton);
        filter = (Button) findViewById(R.id.filterButton);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_empregos);

        search = (SearchView) findViewById(R.id.pesquisa);
        search.setQueryHint("City");

        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                //Toast.makeText(getBaseContext(), String.valueOf(hashCode()),Toast.LENGTH_LONG).show();
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getBaseContext(), query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(getBaseContext(), newText, Toast.LENGTH_SHORT).show();


                return false;
            }
        });

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


    //Obtem as Vagas salvas no Banco de Dados.
    public void filtraVagasDaRequisicao(Filtro filtro){

        List<Vaga> vgs = new ArrayList<>();

        switch (filtro){
            case SEM_FITRO:
                obtemVagasAPI();
                break;
            case MAIOR_SALARIO:
                //vgs = dao.getAllOrderBy("salario");
                break;
            case ULTIMAS_VAGAS:
                //vgs = dao.getAllOrderBy("id");
                break;
            default:
                break;
        }

        //Caso não houver vaga, informa ao usuario com um toast
        if(vgs.isEmpty()) Toast.makeText(this,"Você ainda não possui vagas favoritas.",Toast.LENGTH_LONG).show();

        this.vagas = vgs;
    }


    // obtem todas as vagas da api.
    public void obtemVagasAPI(){
        RequestURL req = new RequestURL(this);
        //Testa a requisição.
        req.requestURL("http://192.168.0.106:10555/vagas?tipoOrdenacao=" + filtroIndex, new RequestURL.VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                VagasJSON vagasJSON = gson.fromJson(response, VagasJSON.class);
                vagas = vagasJSON.getVagas();
                carregaRecyclerView();
            }
        });

    }

    /************************************* Filtros ******************************** */

    // Constrói uma caixa de diálogo que pede qual filtro o jovem deseja.
    private void dialogFiltro(){

        final CharSequence[] charSequences = new CharSequence[]{"Últimas vagas", "Maior Salario"};
        final Integer[]checados = new Integer[charSequences.length];

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha o filtro?");
        builder.setSingleChoiceItems(charSequences, --filtroIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                filtroEscolhido = charSequences[i].toString();
                filtroIndex = i;
                filtroIndex++;
                alerta.dismiss();
                Toast.makeText(getBaseContext(), filtroEscolhido, Toast.LENGTH_SHORT).show();
                mostrarDialogoCarregando();
                obtemVagasAPI();
            }
        });
        alerta = builder.create();
        alerta.show();
    }

    // responsável pelo click do botão filtro.
    public void filterClick(View view){
        dialogFiltro();
    }

    public void mostrarDialogoCarregando(){

        dialog = new ProgressDialog(this);
        dialog.setMessage("Carregando dados");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

    }
}
