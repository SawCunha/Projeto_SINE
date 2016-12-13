package trabalho.sine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trabalho.sine.activity.FragmentDrawer;
import trabalho.sine.function.Conexao;

public class SearchForGraphicActivity extends AppCompatActivity implements  FragmentDrawer.FragmentDrawerListener{

    private Toolbar mToolbar;
    private FragmentDrawer mDrawerFragment;

    @BindView(R.id.cargo_search_edit_text) EditText cargoSearchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_for_graphic);

        ButterKnife.bind(this);
        ButterKnife.setDebug(true);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        createToolbar();
    }

    @OnClick(R.id.search_button)
    public void pesquisarCargo(){

        Toast.makeText(this, "Teste ButterKniffe", Toast.LENGTH_SHORT).show();

    }

    //Responsavel pela criação e definção do toolbar
    private void createToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDrawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer_graphic);
        mDrawerFragment.setUp(R.id.fragment_navigation_drawer_graphic, (DrawerLayout) findViewById(R.id.activity_graphic), mToolbar);
        mDrawerFragment.setDrawerListener(this);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        switch (position){
            case 0: home(); break;
            case 1: searchActivity(); break;
            case 2: favoriteActivity(view); break;
            case 3: break;
            case 4: break;
            default: Log.i("ERRO","POSITION ERROR"); break;
        }
    }

    private void home() {
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
    }

    public void favoriteActivity(View view){
        Intent favoriteActivity = new Intent(this, FavoriteActivity.class);
        startActivity(favoriteActivity);
    }

    private void searchActivity() {
        if(Conexao.isConectado(this)) {
            Intent searchActivity = new Intent(this, SearchActivity.class);
            startActivity(searchActivity);
        }else
            Toast.makeText(this,R.string.conexao_infor,Toast.LENGTH_LONG).show();
    }


}
