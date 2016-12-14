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

import trabalho.sine.activity.FragmentDrawer;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.function.Conexao;
import trabalho.sine.model.Vaga;

public class ResultActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private ImageButton favoriteBtn;
    private ImageButton shareBtn;
    private Vaga vaga;
    private ImageView openLink;

    private TextView title, money, city, address, company, function, des, url;

    private Toolbar mToolbar;
    private FragmentDrawer mDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.titleValue);
        money = (TextView) findViewById(R.id.moneyValue);
        city = (TextView) findViewById(R.id.cityValue);
        address = (TextView) findViewById(R.id.addressValue);
        company = (TextView) findViewById(R.id.companyValue);
        function = (TextView) findViewById(R.id.functionValue);
        des = (TextView) findViewById(R.id.descriptionValue);
        url = (TextView) findViewById(R.id.urlValue);
        openLink = (ImageView) findViewById(R.id.openLinkImage);
        favoriteBtn = (ImageButton) findViewById(R.id.favoritoBTN);
        shareBtn = (ImageButton) findViewById(R.id.shareBTN);

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
            favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_black);
            Toast.makeText(this, R.string.toast_msg_result_activity_favoritado, Toast.LENGTH_SHORT).show();
        } else {
            vagaDAO.delete(vaga);
            vaga.setFavoritado(false);
            favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_border_black_48dp);
            Toast.makeText(this, R.string.toast_msg_result_activity_desfavoritado, Toast.LENGTH_SHORT).show();
        }

    }

    // Compartilha o link do card relacionado a vaga.
    public void shareClick(View view){

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        share.putExtra(Intent.EXTRA_SUBJECT, vaga.getTitulo());
        share.putExtra(Intent.EXTRA_TEXT, vaga.getUrl_sine());

        startActivity(Intent.createChooser(share, "Compartilhar"));

    }

    // Abre o link da vaga no navegador.
    public void openLink(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(vaga.getUrl_sine()));
        startActivity(intent);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        switch (position){
            case 0: home(); break;
            case 1: searchActivity(); break;
            case 2: favoriteActivity(view); break;
            case 3: searchForGraphicActivity();break;
            case 4: info(); break;
            default: Log.i("ERRO","POSITION ERROR"); break;
        }
    }

    private void home() {
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
    }

    private void searchActivity() {
        if(Conexao.isConectado(this)) {
            Intent searchActivity = new Intent(this, SearchActivity.class);
            startActivity(searchActivity);
        }else
            Toast.makeText(this,R.string.conexao_infor,Toast.LENGTH_LONG).show();
    }

    public void favoriteActivity(View view){
        Intent favoriteActivity = new Intent(this, FavoriteActivity.class);
        startActivity(favoriteActivity);
    }

    private void searchForGraphicActivity() {
        if(Conexao.isConectado(this)) {
            Intent searchForGraphicActivity = new Intent(this,SearchForGraphicActivity.class);
            startActivity(searchForGraphicActivity);
        }else
            Toast.makeText(this,R.string.conexao_infor,Toast.LENGTH_LONG).show();
    }

    private void info() {
        Intent info = new Intent(this, InfoActivity.class);
        startActivity(info);
    }
}