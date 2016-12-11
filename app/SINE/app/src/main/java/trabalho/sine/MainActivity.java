package trabalho.sine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void searchActivity(View view){
        Intent searchActivity = new Intent(this,SearchActivity.class);
        startActivity(searchActivity);
    }

    public void favoriteActivity(View view){
        Intent favoriteActivity = new Intent(this,FavoriteActivity.class);
        startActivity(favoriteActivity);
    }

    public void graphicActivity(View view){
        Intent graphicActivity = new Intent(this,GraphicActivity.class);
        startActivity(graphicActivity);
    }

}