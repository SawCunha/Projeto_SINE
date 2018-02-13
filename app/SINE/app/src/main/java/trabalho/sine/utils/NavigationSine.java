package trabalho.sine.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

import trabalho.sine.R;
import trabalho.sine.activity.FavoriteActivity;
import trabalho.sine.activity.InfoActivity;
import trabalho.sine.activity.MainActivity;
import trabalho.sine.activity.SearchActivity;
import trabalho.sine.activity.SearchForGraphicActivity;
import trabalho.sine.function.Conexao;

/**
 * Created by Samuel Cunha on 25/11/17.
 */
public class NavigationSine implements NavigationView.OnNavigationItemSelectedListener {

    private final DrawerLayout drawerLayout;
    private final int idView;
    private final Context context;

    public NavigationSine(DrawerLayout drawerLayout, int idView, Context context) {
        this.drawerLayout = drawerLayout;
        this.idView = idView;
        this.context = context;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home && idView != R.id.home) {
            homeActivity();
        } else if (id == R.id.searchActivity && idView != R.id.searchActivity) {
            searchActivity();
        } else if (id == R.id.searchForGraphicActivity && idView != R.id.searchForGraphicActivity) {
            searchForGraphicActivity();
        } else if (id == R.id.favoriteActivity && idView != R.id.favoriteActivity) {
            favoriteActivity();
        } else if (id == R.id.infoActivity && idView != R.id.infoActivity) {
            infoActivity();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void homeActivity() {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    private void searchActivity() {
        if (Conexao.isConectado(context)) {
            context.startActivity(new Intent(context, SearchActivity.class));
        } else
            Toast.makeText(context, R.string.conexao_infor, Toast.LENGTH_LONG).show();
    }

    private void favoriteActivity() {
        context.startActivity(new Intent(context, FavoriteActivity.class));
    }

    private void searchForGraphicActivity() {
        if (Conexao.isConectado(context)) {
            context.startActivity(new Intent(context, SearchForGraphicActivity.class));
        } else
            Toast.makeText(context, R.string.conexao_infor, Toast.LENGTH_LONG).show();
    }

    private void infoActivity() {
        context.startActivity(new Intent(context, InfoActivity.class));
    }
}
