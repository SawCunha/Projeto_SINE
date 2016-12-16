package trabalho.sine;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import trabalho.sine.activity.FragmentDrawer;
import trabalho.sine.activity.LoadActivities;
import trabalho.sine.adapter.AdapterListView;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.enun.CampoBD;
import trabalho.sine.enun.Filtro;
import trabalho.sine.function.Conexao;
import trabalho.sine.model.Vaga;

public class FavoriteActivity extends AppCompatActivity implements  FragmentDrawer.FragmentDrawerListener{

    private RecyclerView mRecyclerView;
    private AdapterListView mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Vaga> vagas;

    private Toolbar mToolbar;
    private FragmentDrawer mDrawerFragment;

    private String filtroEscolhido = "";
    private int filtroIndex = 1;
    private AlertDialog alerta;

    private Button filtroButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_empregos_favoritos);

        filtroButton = (Button) findViewById(R.id.filterButton);

        createToolbar();
        verficaFiltroSelecionado();
    }

    //Responsavel pela criação e definção do toolbar
    private void createToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDrawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer_favorite);
        mDrawerFragment.setUp(R.id.fragment_navigation_drawer_favorite, (DrawerLayout) findViewById(R.id.activity_favorite), mToolbar);
        mDrawerFragment.setDrawerListener(this);
    }

    private void verficaFiltroSelecionado() {
        obtemVagasBanco(filtroIndex);
        createRecyclerView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        verficaFiltroSelecionado();
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


    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(2,returnIntent);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        switch (position){
            case 0: LoadActivities.home(this); break;
            case 1: LoadActivities.searchActivity(this); break;
            case 2: break;
            case 3: LoadActivities.searchForGraphicActivity(this);break;
            case 4: LoadActivities.info(this); break;
            default: Log.i("ERRO","POSITION ERROR"); break;
        }
    }

    // responsável pelo click do botão filtro.
    public void filterClick(View view){
        dialogFiltro();
    }

}
