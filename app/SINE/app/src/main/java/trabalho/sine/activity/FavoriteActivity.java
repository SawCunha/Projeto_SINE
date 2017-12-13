package trabalho.sine.activity;

import android.app.Dialog;
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
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import trabalho.sine.R;
import trabalho.sine.adapter.FavoriteJobAdapter;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.enun.CampoBD;
import trabalho.sine.model.Vaga;
import trabalho.sine.utils.NavigationSine;

public class FavoriteActivity extends AppCompatActivity{

    @BindView(R.id.list_empregos_favoritos) RecyclerView mRecyclerView;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    private List<Vaga> vagas;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    private int filtroIndex = 1;
    private Dialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        //Define o ButterKnife para gerenciar as activities e ativa o modo de debugação.
        ButterKnife.bind(this);
        ButterKnife.setDebug(true);

        createNavigationView();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        MenuItem filterMenu = menu.findItem(R.id.filterMenu);
        Drawable newIcon = (Drawable)filterMenu.getIcon();
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
        mRecyclerView.setAdapter(new FavoriteJobAdapter(vagas, FavoriteActivity.this));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(FavoriteActivity.this,
                LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layout);

        mRecyclerView.setLayoutFrozen(false);
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
                vgs = dao.getAll();
                break;
        }

        //Caso não houver vaga, informa ao usuario com um toast
        if(vgs.isEmpty()) Toast.makeText(this,R.string.toast_msg_favorite_activity,Toast.LENGTH_LONG).show();

        this.vagas = vgs;
    }

    /************************************* Filtros ******************************** */

    // Constrói uma caixa de diálogo que pede qual filtro o jovem deseja.
    private void dialogFiltro(){

        final int tempFiltroIndex = filtroIndex;

        alerta = new Dialog(this);
        alerta.setContentView(R.layout.alert_dialog_favorite);

        final RadioGroup radioGroup = alerta.findViewById(R.id.grupo);
        final RadioButton buttonUltimas = alerta.findViewById(R.id.ultimasVagas);
        final RadioButton buttonSalario = alerta.findViewById(R.id.maiorSalario);
        Button filterBtn = alerta.findViewById(R.id.filterBtn);
        Button clearBtn = alerta.findViewById(R.id.clearBtn);

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
                verficaFiltroSelecionado();
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtroIndex = 0;
                verficaFiltroSelecionado();
            }
        });

        alerta.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        filtroIndex = tempFiltroIndex;
        alerta.show();
    }

}
