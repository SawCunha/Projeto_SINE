package trabalho.sine;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import trabalho.sine.activity.FragmentDrawer;
import trabalho.sine.activity.LoadActivities;
import trabalho.sine.adapter.AdapterListView;
import trabalho.sine.adapter.AdpterScrollListener;
import trabalho.sine.controller.RequestURL;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.enun.Filtro;
import trabalho.sine.function.Conexao;
import trabalho.sine.model.Vaga;
import trabalho.sine.model.VagasJSON;

public class SearchActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private RecyclerView mRecyclerView;
    private AdapterListView mAdapter;
    private LinearLayoutManager mLayoutManager;
    private Button favorite;
    private List<Vaga> vagas;
    private ProgressDialog dialog;
    private String filtroEscolhido = "";
    private int filtroIndex = 1;
    private AlertDialog alerta;
    private Button filter;

    private String cidadeEstado = "", funcao = "", numPag;
    private AlertDialog buscaAlert;
    private EditText inputCidade;
    private EditText inputFuncao;

    private Toolbar mToolbar;
    private FragmentDrawer mDrawerFragment;
    private int pos = 1;
    private int totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        vagas = new ArrayList<>();

        inputCidade = new EditText(this);
        inputFuncao = new EditText(this);
        totalItemCount = 0;
        inputCidade.setInputType(InputType.TYPE_CLASS_TEXT);
        inputFuncao.setInputType(InputType.TYPE_CLASS_TEXT);
        favorite = (Button) findViewById(R.id.favoriteButton);
        filter = (Button) findViewById(R.id.filterButton);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_empregos);

        //Toolbar e MenuDrawer
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mostrarDialogoCarregando();
        obtemVagasAPI();
        createToolbar();

    }

    private void createToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDrawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer_search);
        mDrawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.activity_search), mToolbar);
        mDrawerFragment.setDrawerListener(this);
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
        mRecyclerView.scrollToPosition(totalItemCount);
        //Define o metodo que ira obter a ação do Scroll.
        mRecyclerView.addOnScrollListener(new AdpterScrollListener(this,mRecyclerView,mAdapter,mLayoutManager,
                cidadeEstado,funcao,filtroIndex,pos));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        verifica();
        createRecyclerView();
    }

    // Verifica quais das vagas está no banco de dados.
    public void verifica() {

        VagaDAO vagaDAO = new VagaDAO(getApplicationContext());
        List<Vaga> vagasBd = vagaDAO.getAll();

        //Define todos os favoritos da vagas para false...
        for(Vaga v : vagas)
            v.setFavoritado(false);

        //Depois verificar quais estão no banco e retornado pela api.
        //Definir para true o campo favoritado.
        for (Vaga vbd : vagasBd)
            for (Vaga vs : vagas)
                if (vbd.getId().toString().equalsIgnoreCase(vs.getId().toString()))
                    vs.setFavoritado(true);
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
        if(vgs.isEmpty()) Toast.makeText(this,R.string.toast_msg_search_activity,Toast.LENGTH_LONG).show();

        this.vagas = vgs;
    }

    // obtem todas as vagas da api.
    public void obtemVagasAPI(){
        RequestURL req = new RequestURL(this);

        req.requestURL(String.format("http://192.168.0.101:10555/vagas?idfuncao=%s&idcidade=%s&numPagina=%d" +
                "&tipoOrdenacao=%d", funcao, cidadeEstado, pos, filtroIndex), new RequestURL.VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                VagasJSON vagasJSON = gson.fromJson(response, VagasJSON.class);
                vagas.addAll(vagasJSON.getVagas());
                carregaRecyclerView();
            }
        });

    }

    /************************************* Filtros ******************************** */

    // Constrói uma caixa de diálogo que pede qual filtro o jovem deseja.
    private void dialogFiltro(){

        // Os blocos abaixo define os input's para a entrada de texto.
        inputCidade.setHint("Cidade-uf");
        inputFuncao.setHint("Função");

        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(inputCidade);
        layout.addView(inputFuncao);

        // Guardar o ultimo filtro clicado, se o jovem nao clicar em um novo, volta ao que estava.
        final int tempFiltroIndex = filtroIndex;

        final CharSequence[] charSequences = new CharSequence[]{"Últimas vagas", "Maior Salario"};
        final Integer[]checados = new Integer[charSequences.length];


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha o filtro?");
        builder.setView(layout);
        builder.setSingleChoiceItems(charSequences, --filtroIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                filtroEscolhido = charSequences[i].toString();
                filtroIndex = i;
                filtroIndex++;
            }
        });

        // Ação que irá ocorrer quando o jovem clicar no botão ok.
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                cidadeEstado = inputCidade.getText().toString().replaceAll(" ", "").trim();
                funcao = inputFuncao.getText().toString().replaceAll(" ", "").trim();

                alerta.dismiss();

                /* Remove os input's do layout, como eles são variavéis de classe(fiz isso pra manter o estado da última pesquisa),
                   o estado de qual layout eles pertence é mantido, assim se o jovem fechar o alert e abrir de novo não seria
                   possível add's em novo layout, por isso esse comando abaixo.
                */
                layout.removeAllViews();

               // Reseta o scroll
                mRecyclerView.scrollToPosition(0);
                mRecyclerView.clearOnScrollListeners();

                mostrarDialogoCarregando();
                vagas.clear();
                pos = 1;
                obtemVagasAPI();
            }
        });

        // Ação que irá ocorrer quando o jovem clicar no botão Reseta.
        builder.setNegativeButton("Resetar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // Requisicão feita será: /vagas?idfuncao=&idcidade=?&numPagina=?tipoOrdenacao=1

                // Reseta todos os campos.

                // Reseta o scroll.
                mRecyclerView.scrollToPosition(0);
                mRecyclerView.clearOnScrollListeners();

                vagas.clear();
                pos = 1;
                filtroEscolhido = "";
                filtroIndex = 1;
                inputCidade.setText("");
                inputFuncao.setText("");

                cidadeEstado = "";
                funcao = "";

                alerta.dismiss();
                layout.removeAllViews();

                mostrarDialogoCarregando();
                obtemVagasAPI();
            }
        });


        filtroIndex = tempFiltroIndex;
        builder.setCancelable(false);
        alerta = builder.create();
        alerta.show();
    }

    // responsável pelo click do botão filtro.
    public void filterClick(View view){
        dialogFiltro();
    }

    /* Constrói uma caixa de diálogo para montar a busca.
    private void dialogBusca(){

        final EditText inputCidade = new EditText(this);
        final EditText inputFuncao = new EditText(this);

        inputCidade.setHint("Cidade-un");
        inputFuncao.setHint("Função");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(inputCidade);
        layout.addView(inputFuncao);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("         Entre com a busca!");
        builder.setView(layout);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cidadeEstado = inputCidade.getText().toString();
                funcao = inputFuncao.getText().toString();
                buscaAlert.dismiss();
                mostrarDialogoCarregando();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                buscaAlert.dismiss();
            }
        });

        builder.setCancelable(false);
        buscaAlert = builder.create();
        buscaAlert.show();
    }

    public void searchClick(View view){

        dialogBusca();

    }*/


    public void mostrarDialogoCarregando(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Carregando dados");
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        switch (position){
            case 0: LoadActivities.home(this); break;
            case 1: break;
            case 2: LoadActivities.favoriteActivity(this); break;
            case 3: LoadActivities.searchForGraphicActivity(this);break;
            case 4: LoadActivities.info(this); break;
            default: Log.i("ERRO","POSITION ERROR"); break;
        }
    }

}
