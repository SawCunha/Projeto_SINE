package trabalho.sine.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trabalho.sine.R;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.model.Vaga;
import trabalho.sine.utils.NavigationSine;

public class ResultActivity extends AppCompatActivity{

    @BindView(R.id.favoriteFloat) FloatingActionButton favoriteBtn;
    @BindView(R.id.shareBTN) FloatingActionButton shareBtn;
    private Vaga vaga;
    @BindView(R.id.openLink) FloatingActionButton openLink;

    @BindView(R.id.titleValue) TextView title;
    @BindView(R.id.moneyValue) TextView money;
    @BindView(R.id.cityValue) TextView city;
    @BindView(R.id.addressValue) TextView address;
    @BindView(R.id.companyValue) TextView company;
    @BindView(R.id.functionValue) TextView function;
    @BindView(R.id.descriptionValue) TextView des;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Define o ButterKnife para gerenciar as activities e ativa o modo de debugação.
        ButterKnife.bind(this);
        ButterKnife.setDebug(true);

        createNavigationView();
        carregaInforActivity(getIntent().getExtras());

    }

    private void createNavigationView(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_result);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationSine(drawerLayout,-1,this));
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("position",position);
            setResult(RESULT_OK,returnIntent);
            super.onBackPressed();
        }
    }

    private void carregaInforActivity(Bundle bundle) {
        String vagaJson = (String) bundle.get("vaga");
        position = (int) bundle.get("position");
        vaga = new Gson().fromJson(vagaJson, Vaga.class);

        populaActivity(vaga);

    }

    public void populaActivity(Vaga vaga){
        favoriteBtn.setImageResource(
                (vaga.isFavoritado() == false ?
                        R.drawable.favorite_border : R.drawable.favorite_black));
        if (vaga.getTitulo() != null && vaga.getTitulo().trim().length() > 0)
            title.setText(vaga.getTitulo());
        else
            title.setText(R.string.titulo_nao_informado);

        if (vaga.getSalario() != null && vaga.getSalario().trim().length() > 0)
            money.setText(vaga.getSalario());
        else
            money.setText(R.string.salario_nao_informado);

        if (vaga.getCidade() != null && vaga.getCidade().trim().length() > 0)
            city.setText(vaga.getCidade());
        else
            city.setText(R.string.cidade_nao_informada);

        if (vaga.getEndereco() != null && vaga.getEndereco().trim().length() > 0)
            address.setText(vaga.getEndereco());
        else
            address.setText(R.string.endereco_nao_informado);

        if (vaga.getEmpresa() != null && vaga.getEmpresa().trim().length() > 0)
            company.setText(vaga.getEmpresa());
        else
            company.setText(R.string.empresa_nao_informada);

        if (vaga.getFuncao() != null && vaga.getFuncao().trim().length() > 0)
            function.setText(vaga.getFuncao());
        else
            function.setText(R.string.funcao_nao_informada);

        if (vaga.getDescricao() != null && vaga.getDescricao().trim().length() > 0)
            des.setText(vaga.getDescricao());
        else
            des.setText(R.string.descricao_nao_informada);
    }

    @OnClick(R.id.favoriteFloat)
    public void favoriteClick(View view) {
        VagaDAO vagaDAO = new VagaDAO(this.getApplicationContext());
        if (vaga.isFavoritado() == false) {
            vaga.setFavoritado(true);
            vagaDAO.insert(vaga);
            favoriteBtn.setImageResource(R.drawable.favorite_black);
            //Toast.makeText(this, R.string.toast_msg_result_activity_favoritado, Toast.LENGTH_SHORT).show();
        } else {
            vagaDAO.delete(vaga);
            vaga.setFavoritado(false);
            favoriteBtn.setImageResource(R.drawable.favorite_border);
           // Toast.makeText(this, R.string.toast_msg_result_activity_desfavoritado, Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.shareBTN)
    // Compartilha o link do card relacionado a vaga.
    public void shareClick(View view){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        share.putExtra(Intent.EXTRA_SUBJECT, vaga.getTitulo());
        share.putExtra(Intent.EXTRA_TEXT, vaga.getUrl_sine());

        startActivity(Intent.createChooser(share,"Compartilhar"));
    }

    @OnClick(R.id.openLink)
    // Abre o link da vaga no navegador.
    public void openLink(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(vaga.getUrl_sine()));
        startActivity(intent);
    }

}