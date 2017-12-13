package trabalho.sine.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import trabalho.sine.R;
import trabalho.sine.adapter.AdpterScrollListener;
import trabalho.sine.adapter.CargoSuggestionAdapter;
import trabalho.sine.adapter.CidadeSuggestionAdapter;
import trabalho.sine.adapter.JobAdapter;
import trabalho.sine.controller.RequestURL;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.model.Cargo;
import trabalho.sine.model.Cidade;
import trabalho.sine.model.Vaga;
import trabalho.sine.model.VagasJSON;
import trabalho.sine.utils.Constantes;
import trabalho.sine.utils.Functions;
import trabalho.sine.utils.NavigationSine;

public class SearchActivity extends AppCompatActivity{

    @BindView(R.id.list_empregos) RecyclerView mRecyclerView;

    private List<Vaga> vagas;
    private ProgressDialog dialog;
    private int filtroIndex = 1;
    private Dialog alerta;

    private String cityValue = "", functionValue = "";

    private Long cidadeEstado = 0l, funcao = 0l;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    private int pos = 1;
    private int totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Define o ButterKnife para gerenciar as activities e ativa o modo de debugação.
        ButterKnife.bind(this);
        ButterKnife.setDebug(true);

        createNavigationView();

        totalItemCount = 0;

        vagas = new ArrayList();
        vagas.add(null);

        carregaRecyclerView();

        obtemVagasGeral();

    }

    private void createNavigationView(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout_search);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationSine(drawerLayout,R.id.searchActivity,this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        MenuItem filterMenu = menu.findItem(R.id.filterMenu);
        Drawable newIcon = filterMenu.getIcon();
        newIcon.mutate().setColorFilter(Color.argb(255, 255, 255, 255), PorterDuff.Mode.SRC_IN);
        filterMenu.setIcon(newIcon);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.filterMenu) dialogFiltro();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            NavUtils.navigateUpFromSameTask(this);
        }
    }

    private void criaAutoComplete(AutoCompleteTextView inputCargo, AutoCompleteTextView inputCidade) {
        inputCargo.setAdapter(new CargoSuggestionAdapter(this, inputCargo.getText().toString(), Constantes.URL_API + "/idfuncao/"));

        inputCargo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cargo c = (Cargo) adapterView.getItemAtPosition(position);
                funcao = c.getId();
                functionValue = c.getDescricao();
            }
        });

        inputCidade.setAdapter(new CidadeSuggestionAdapter(this, inputCidade.getText().toString(), Constantes.URL_API + "/idcidade/"));

        inputCidade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cidade c = (Cidade) adapterView.getItemAtPosition(position);
                cidadeEstado = c.getId();
                cityValue = c.getDescricao();
            }
        });
    }


    private void carregaRecyclerView() {
        verifica();
        createRecyclerView();
    }

    private void createRecyclerView(){

        //Remove os itens do Recycler, para add os novos valores.
        mRecyclerView.removeAllViewsInLayout();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new JobAdapter(vagas, SearchActivity.this));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(SearchActivity.this,
                LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layout);

        mRecyclerView.setLayoutFrozen(false);
        mRecyclerView.scrollToPosition(totalItemCount);
        //Define o metodo que ira obter a ação do Scroll.
        mRecyclerView.addOnScrollListener(new AdpterScrollListener(this,mRecyclerView,
                (JobAdapter) mRecyclerView.getAdapter(),layout,
                cidadeEstado,funcao,filtroIndex,pos));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        verifica();
        createRecyclerView();
        mRecyclerView.scrollToPosition((int)data.getExtras().get("position"));
    }

    // Verifica quais das vagas está no banco de dados.
    public void verifica() {

        VagaDAO vagaDAO = new VagaDAO(getApplicationContext());
        List<Vaga> vagasBd = vagaDAO.getAll();

        //Define todos os favoritos da vagas para false...
        for (Vaga v : vagas)
            if (v != null) v.setFavoritado(false);

        //Depois verificar quais estão no banco e retornado pela api.
        //Definir para true o campo favoritado.
        for (Vaga vbd : vagasBd)
            for (Vaga vs : vagas)
                if (vs != null)
                    if (vbd.getId().toString().equalsIgnoreCase(vs.getId().toString()))
                        vs.setFavoritado(true);
    }

    // obtem todas as vagas da api.
    public void obtemVagasAPI(){
        RequestURL req = new RequestURL(this);

        req.requestURL(String.format(Constantes.URL_API + Constantes.URL_API_VAGAS, funcao, cidadeEstado, pos, filtroIndex), new RequestURL.VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                VagasJSON vagasJSON = gson.fromJson(response, VagasJSON.class);
                vagas.remove(0);
                vagas.addAll(vagasJSON.getVagas());
                carregaRecyclerView();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("Error",error.getMessage());
                }catch (Exception e){
                    Functions.DialogErro(SearchActivity.this, "Erro", "Não foi possivel obter as Vagas.");
                    e.printStackTrace();
                }
            }
        });

    }

    // obtem todas as vagas da api.
    public void obtemVagasGeral(){
        RequestURL req = new RequestURL(this);

        req.requestURL(Constantes.URL_API_VAGAS_GERAL, new RequestURL.VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                VagasJSON vagasJSON = gson.fromJson(response, VagasJSON.class);
                vagas.remove(0);
                vagas.addAll(vagasJSON.getVagas());
                carregaRecyclerView();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("Error",error.getMessage());
                }catch (Exception e){
                    Functions.DialogErro(SearchActivity.this, "Erro", "Não foi possivel obter as Vagas.");
                    e.printStackTrace();
                }
            }
        });

    }

    /************************************* Filtros ******************************** */

    // Constrói uma caixa de diálogo que pede qual filtro o jovem deseja.
    private void dialogFiltro(){

        final int tempFiltroIndex = filtroIndex;

        alerta = new Dialog(this);
        alerta.setContentView(R.layout.alert_dialog_search);

        final RadioGroup radioGroup = alerta.findViewById(R.id.grupo);
        final RadioButton buttonUltimas = alerta.findViewById(R.id.ultimasVagas);
        final RadioButton buttonSalario = alerta.findViewById(R.id.maiorSalario);
        Button filterBtn = alerta.findViewById(R.id.filterBtn);
        Button clearBtn = alerta.findViewById(R.id.clearBtn);
        final AutoCompleteTextView city = alerta.findViewById(R.id.cidade);
        final AutoCompleteTextView function = alerta.findViewById(R.id.funcao);

        city.setText(cityValue);
        function.setText(functionValue);

        criaAutoComplete(function,city);

        if(filtroIndex == 1)
            radioGroup.check(buttonUltimas.getId());
        else
            radioGroup.check(buttonSalario.getId());

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alerta.dismiss();

                if(radioGroup.getCheckedRadioButtonId() == buttonUltimas.getId())
                    filtroIndex = 1;
                else
                    filtroIndex = 2;

                mRecyclerView.scrollToPosition(0);
                mRecyclerView.clearOnScrollListeners();

                vagas.clear();
                vagas.add(null);
                createRecyclerView();

                pos = 1;
                obtemVagasAPI();
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reseta o scroll.
                mRecyclerView.scrollToPosition(0);
                mRecyclerView.clearOnScrollListeners();

                vagas.clear();
                pos = 1;
                filtroIndex = 1;

                cidadeEstado = 0l;
                funcao = 0l;
                cityValue = "";
                functionValue = "";
                city.setText("");
                function.setText("");
                alerta.dismiss();

                vagas.clear();
                vagas.add(null);
                createRecyclerView();

                obtemVagasAPI();
            }
        });

        alerta.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        filtroIndex = tempFiltroIndex;
        alerta.show();
    }
}
