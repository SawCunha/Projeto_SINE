package trabalho.sine.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trabalho.sine.R;
import trabalho.sine.adapter.AdapterListView;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.enun.CampoBD;
import trabalho.sine.model.Vaga;
import trabalho.sine.utils.NavigationSine;

public class FavoriteActivity extends AppCompatActivity{

    @BindView(R.id.list_empregos_favoritos) RecyclerView mRecyclerView;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    private AdapterListView mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Vaga> vagas;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    private String filtroEscolhido = "";
    private int filtroIndex = 1;
    private AlertDialog alerta;

    private Button filtroButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        //Define o ButterKnife para gerenciar as activities e ativa o modo de debugação.
        ButterKnife.bind(this);
        ButterKnife.setDebug(true);

        createNavigationView();

        filtroButton = findViewById(R.id.filterButton);

        verficaFiltroSelecionado();
    }

    private void createNavigationView(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_favorite);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationSine(drawerLayout,R.id.favoriteActivity,this));
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void verficaFiltroSelecionado() {
        obtemVagasBanco(filtroIndex);
        createRecyclerView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        verficaFiltroSelecionado();
        mRecyclerView.scrollToPosition((int)data.getExtras().get("position"));
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
    public void obtemVagasBanco(int filtro){
        List<Vaga> vgs = new ArrayList<>();

        VagaDAO dao = new VagaDAO(getApplicationContext());

        switch (filtro){
            case 1:
                vgs = dao.getAllOrderBy(CampoBD.ID.toString());
                break;

            case 2:
                vgs = dao.getAllOrderBy(CampoBD.SALARIO.toString());
                Collections.sort(vgs);
                break;

            default:
                break;
        }

        //Caso não houver vaga, informa ao usuario com um toast
        if(vgs.isEmpty()) Toast.makeText(this,R.string.toast_msg_favorite_activity,Toast.LENGTH_LONG).show();

        this.vagas = vgs;
    }

    /************************************* Filtros ******************************** */

    // Constrói uma caixa de diálogo que pede qual filtro o jovem deseja.
    private void dialogFiltro(){

        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Guardar o ultimo filtro clicado, se o jovem nao clicar em um novo, volta ao que estava.
        final int tempFiltroIndex = filtroIndex;

        final CharSequence[] charSequences = new CharSequence[]{"Últimas vagas", "Maior FaixaSalarial"};
        final Integer[]checados = new Integer[charSequences.length];

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_dialog_title);
        builder.setView(layout);

        // Ação que irá ocorrer quando o jovem clicar no botão ok.
        builder.setSingleChoiceItems(charSequences, --filtroIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                filtroEscolhido = charSequences[i].toString();
                filtroIndex = i;
                filtroIndex++;
                verficaFiltroSelecionado();
                alerta.dismiss();

            }
        });

        filtroIndex = tempFiltroIndex;
        builder.setCancelable(true);
        alerta = builder.create();
        alerta.show();
    }

    @OnClick(R.id.filter)
    // responsável pelo click do botão filtro.
    public void filterClick(View view){
        dialogFiltro();
    }

}
