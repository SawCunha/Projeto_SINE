package trabalho.sine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import trabalho.sine.function.Conexao;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void searchActivity(View view){
        if(Conexao.isConectado(this)) {
            Intent searchActivity = new Intent(this, SearchActivity.class);
            startActivity(searchActivity);
        }else
            Toast.makeText(this,R.string.conexao_infor,Toast.LENGTH_LONG).show();
    }

    public void favoriteActivity(View view){
        Intent favoriteActivity = new Intent(this,FavoriteActivity.class);
        startActivity(favoriteActivity);
    }

    public void graphicActivity(View view){
        if(Conexao.isConectado(this)) {
            Intent graphicActivity = new Intent(this,GraphicActivity.class);
            startActivity(graphicActivity);
        }else
            Toast.makeText(this,R.string.conexao_infor,Toast.LENGTH_LONG).show();
    }

}