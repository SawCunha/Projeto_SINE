package trabalho.sine;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import trabalho.sine.activity.FragmentDrawer;
import trabalho.sine.activity.LoadActivities;
import trabalho.sine.adapter.AdapterListView;
import trabalho.sine.adapter.AdpterScrollListener;
import trabalho.sine.adapter.CargoSuggestionAdapter;
import trabalho.sine.adapter.CidadeSuggestionAdapter;
import trabalho.sine.controller.RequestURL;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.model.Cargo;
import trabalho.sine.model.Cidade;
import trabalho.sine.model.Vaga;
import trabalho.sine.model.VagasJSON;
import trabalho.sine.utils.Constantes;

public class SearchActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {


    private RecyclerView mRecyclerView;
    private AdapterListView mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<Vaga> vagas;
    private ProgressDialog dialog;
    private String filtroEscolhido = "";
    private int filtroIndex = 1;
    private AlertDialog alerta;
    private Button filter;

    private Long cidadeEstado = 0l, funcao = 0l;
    private AutoCompleteTextView inputCidade;
    private AutoCompleteTextView inputFuncao;

    private Toolbar mToolbar;
    private FragmentDrawer mDrawerFragment;
    private int pos = 1;
    private int totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        vagas = new ArrayList<>();
        mRecyclerView = (RecyclerView)findViewById(R.id.list_empregos);
        inputCidade = new AutoCompleteTextView(this);
        inputFuncao = new AutoCompleteTextView(this);
        totalItemCount = 0;
        inputCidade.setInputType(InputType.TYPE_CLASS_TEXT);
        inputFuncao.setInputType(InputType.TYPE_CLASS_TEXT);
        filter = (Button) findViewById(R.id.filterButton);

        //Toolbar e MenuDrawer
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        criaAutoComplete();
        mostrarDialogoCarregando();
        obtemVagasAPI();
        createToolbar();

    }

    private void criaAutoComplete() {
        inputFuncao.setAdapter(new CargoSuggestionAdapter(this, inputFuncao.getText().toString(), Constantes.URL_API + "/idfuncao/"));

        inputFuncao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cargo c = (Cargo) adapterView.getItemAtPosition(position);
                funcao = c.getId();
            }
        });

        inputCidade.setAdapter(new CidadeSuggestionAdapter(this, inputCidade.getText().toString(), Constantes.URL_API + "/idcidade/"));

        inputCidade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cidade c = (Cidade) adapterView.getItemAtPosition(position);
                cidadeEstado = c.getId();
            }
        });
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

    // obtem todas as vagas da api.
    public void obtemVagasAPI(){
        RequestURL req = new RequestURL(this);

        req.requestURL(String.format(Constantes.URL_API + "/vagas?idfuncao=%d&idcidade=%d&numPagina=%d" +
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

        final int tempFiltroIndex = filtroIndex;

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View forms = inflater.inflate(R.layout.alert_dialog, null, false);

        final RadioGroup radioGroup = (RadioGroup) forms.findViewById(R.id.grupo);
        final RadioButton buttonUltimas = (RadioButton) forms.findViewById(R.id.ultimasVagas);
        final RadioButton buttonSalario = (RadioButton) forms.findViewById(R.id.maiorSalario);

        AutoCompleteTextView city = (AutoCompleteTextView) forms.findViewById(R.id.cidade);
        AutoCompleteTextView function = (AutoCompleteTextView) forms.findViewById(R.id.funcao);

        //inputCidade = city;
        //inputFuncao = function;


        if(filtroIndex == 1)
            radioGroup.check(buttonUltimas.getId());
        else
            radioGroup.check(buttonSalario.getId());


        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(forms);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha o filtro?");
        builder.setView(layout);

        // Ação que irá ocorrer quando o jovem clicar no botão ok.
        builder.setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                alerta.dismiss();

                /* Remove os input's do layout, como eles são variavéis de classe(fiz isso pra manter o estado da última pesquisa),
                   o estado de qual layout eles pertence é mantido, assim se o jovem fechar o alert e abrir de novo não seria
                   possível add's em novo layout, por isso esse comando abaixo.
                */
                // layout.removeAllViews();

                // Reseta o scroll

                if(radioGroup.getCheckedRadioButtonId() == buttonUltimas.getId())
                    filtroIndex = 1;
                else
                    filtroIndex = 2;
                Toast.makeText(getBaseContext(), "id: " + filtroIndex, Toast.LENGTH_LONG).show();

                mRecyclerView.scrollToPosition(0);
                mRecyclerView.clearOnScrollListeners();

                mostrarDialogoCarregando();
                vagas.clear();
                pos = 1;
                obtemVagasAPI();
            }
        });

        // Ação que irá ocorrer quando o jovem clicar no botão Reseta.
        builder.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
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

                cidadeEstado = 0l;
                funcao = 0l;

                alerta.dismiss();
                //layout.removeAllViews();

                mostrarDialogoCarregando();
                obtemVagasAPI();
            }
        });

        filtroIndex = tempFiltroIndex;
        // filtroIndex = tempFiltroIndex;
        builder.setCancelable(false);
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
