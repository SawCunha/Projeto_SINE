package trabalho.sine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import trabalho.sine.controller.RequestURL;
import trabalho.sine.model.VolleyCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestURL.requestURL("https://cinevertentes.herokuapp.com/api/v1/cineplaza/movies", this, new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
