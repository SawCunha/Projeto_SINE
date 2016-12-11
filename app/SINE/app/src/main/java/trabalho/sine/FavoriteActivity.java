package trabalho.sine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import trabalho.sine.adapter.AdapterListView;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.model.Vaga;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdapterListView mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_empregos_favoritos);
        createRecyclerView();

    }

    private void createRecyclerView(){
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterListView(gera(),this);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutFrozen(true);
    }

    //Método de teste...
    public List<Vaga> gera(){
        List<Vaga> vagas = new ArrayList<>();

        VagaDAO dao = new VagaDAO(getApplicationContext());

        vagas = dao.getAll();

//        Log.d("favorites", vagas.get(0).getId().toString());

        return vagas;
    }

    @Override
    public void onBackPressed() {
        //
        Toast.makeText(this,"To aki no favorite",Toast.LENGTH_SHORT).show();
        Intent returnIntent = new Intent();
        setResult(2,returnIntent);
        super.onBackPressed();
    }

}
