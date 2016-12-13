package trabalho.sine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import trabalho.sine.activity.FragmentDrawer;
import trabalho.sine.function.Conexao;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private Toolbar mToolbar;
    private FragmentDrawer mDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        createToolbar();
    }

    private void createToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDrawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mDrawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        mDrawerFragment.setDrawerListener(this);
    }

    public void searchActivity(View view){
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

    public void searchForGraphicActivity(View view){
        if(Conexao.isConectado(this)) {
            Intent searchForGraphicActivity = new Intent(this, SearchForGraphicActivity.class);
            startActivity(searchForGraphicActivity);
        }else
            Toast.makeText(this,R.string.conexao_infor,Toast.LENGTH_LONG).show();
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
            case 0: break;
            case 1: searchActivity(view); break;
            case 2: favoriteActivity(view); break;
            case 3: searchForGraphicActivity(view);break;
            case 4: break;
            default: Log.i("ERRO","POSITION ERROR"); break;
        }
    }

}