package trabalho.sine;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import trabalho.sine.activity.FragmentDrawer;
import trabalho.sine.activity.LoadActivities;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.function.Conexao;
import trabalho.sine.model.Vaga;

public class ResultActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    @BindView(R.id.favoritoBTN) ImageButton favoriteBtn;
    @BindView(R.id.shareBTN) ImageButton shareBtn;
    private Vaga vaga;
    @BindView(R.id.openLinkImage) ImageView openLink;

    @BindView(R.id.titleValue) TextView title;
    @BindView(R.id.moneyValue) TextView money;
    @BindView(R.id.cityValue) TextView city;
    @BindView(R.id.addressValue) TextView address;
    @BindView(R.id.companyValue) TextView company;
    @BindView(R.id.functionValue) TextView function;
    @BindView(R.id.descriptionValue) TextView des;
    @BindView(R.id.urlValue) TextView url;

    @BindView(R.id.toolbar) Toolbar mToolbar;

    private FragmentDrawer mDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Define o ButterKnife para gerenciar as activities e ativa o modo de debugação.
        ButterKnife.bind(this);
        ButterKnife.setDebug(true);

        createToolbar();
        carregaInforActivity(getIntent().getExtras());

    }

    //Responsavel pela criação e definção do toolbar
    private void createToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDrawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer_result);
        mDrawerFragment.setUp(R.id.fragment_navigation_drawer_result, (DrawerLayout) findViewById(R.id.activity_result), mToolbar);
        mDrawerFragment.setDrawerListener(this);
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        String vagaJson = new Gson().toJson(vaga);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("resultado",vagaJson);
        setResult(RESULT_OK,returnIntent);
        super.onBackPressed();
    }

    private void carregaInforActivity(Bundle bundle) {
        String vagaJson = (String) bundle.get("vaga");

        vaga = new Gson().fromJson(vagaJson, Vaga.class);

        populaActivity(vaga);

    }

    public void populaActivity(Vaga vaga){
        favoriteBtn.setBackgroundResource(
                (vaga.isFavoritado() == false ?
                        R.drawable.ic_favorite_border_black_48dp : R.drawable.ic_favorite_black));
        title.setText(vaga.getTitulo());
        money.setText(vaga.getSalario());
        city.setText(vaga.getCidade());
        address.setText(vaga.getEndereco());
        company.setText(vaga.getEmpresa());
        function.setText(vaga.getFuncao());
        des.setText(vaga.getDescricao());
        url.setText(vaga.getUrl_sine());
    }

    public void favoriteClick(View view) {
        VagaDAO vagaDAO = new VagaDAO(this.getApplicationContext());
        if (vaga.isFavoritado() == false) {
            vaga.setFavoritado(true);
            vagaDAO.insert(vaga);
            favoriteBtn.setBackgroundResource(R.drawable.starrate);
            //Toast.makeText(this, R.string.toast_msg_result_activity_favoritado, Toast.LENGTH_SHORT).show();
        } else {
            vagaDAO.delete(vaga);
            vaga.setFavoritado(false);
            favoriteBtn.setBackgroundResource(R.drawable.starunrate);
           // Toast.makeText(this, R.string.toast_msg_result_activity_desfavoritado, Toast.LENGTH_SHORT).show();
        }

    }

    // Compartilha o link do card relacionado a vaga.
    public void shareClick(View view){

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        share.putExtra(Intent.EXTRA_SUBJECT, vaga.getTitulo());
        share.putExtra(Intent.EXTRA_TEXT, vaga.getUrl_sine());

        startActivity(Intent.createChooser(share,"Compartilhar"));

    }

    // Abre o link da vaga no navegador.
    public void openLink(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(vaga.getUrl_sine()));
        startActivity(intent);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        switch (position){
            case 0: LoadActivities.home(this); break;
            case 1: LoadActivities.searchActivity(this); break;
            case 2: LoadActivities.favoriteActivity(this); break;
            case 3: LoadActivities.searchForGraphicActivity(this);break;
            case 4: LoadActivities.info(this); break;
            default: Log.i("ERRO","POSITION ERROR"); break;
        }
    }

}