package trabalho.sine.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import trabalho.sine.FavoriteActivity;
import trabalho.sine.InfoActivity;
import trabalho.sine.MainActivity;
import trabalho.sine.R;
import trabalho.sine.SearchActivity;
import trabalho.sine.SearchForGraphicActivity;
import trabalho.sine.function.Conexao;

/**
 * Created by saw on 15/12/16.
 */

public class LoadActivities {

    public static void home(Context context) {
        Intent home = new Intent(context, MainActivity.class);
        context.startActivity(home);
    }

    public static void favoriteActivity(Context context) {
        Intent favoriteActivity = new Intent(context, FavoriteActivity.class);
        context.startActivity(favoriteActivity);
    }

    public static void searchActivity(Context context) {
        if(Conexao.isConectado(context)) {
            Intent searchActivity = new Intent(context, SearchActivity.class);
            context.startActivity(searchActivity);
        }else
            Toast.makeText(context,R.string.conexao_infor,Toast.LENGTH_LONG).show();
    }

    public static void searchForGraphicActivity(Context context) {
        if(Conexao.isConectado(context)) {
            Intent searchForGraphicActivity = new Intent(context,SearchForGraphicActivity.class);
            context.startActivity(searchForGraphicActivity);
        }else
            Toast.makeText(context, R.string.conexao_infor,Toast.LENGTH_LONG).show();
    }

    public static void info(Context context) {
        Intent info = new Intent(context, InfoActivity.class);
        context.startActivity(info);
    }
}
