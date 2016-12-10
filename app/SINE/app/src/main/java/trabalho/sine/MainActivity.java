package trabalho.sine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import trabalho.sine.adapter.AdapterListView;
import trabalho.sine.controller.RequestURL;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.dao.VagasJSON;
import trabalho.sine.model.Vaga;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdapterListView mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favorite = (Button) findViewById(R.id.favoriteButton);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_empregos);
        createRecyclerView();
    }

    private void createRecyclerView(){

        // Não é legal, está deletando e criando o recycler view novamente.
        mRecyclerView.removeAllViewsInLayout();

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

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(this, "OnRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this, resultCode + " = " + RESULT_OK, Toast.LENGTH_SHORT).show();
        if(resultCode == RESULT_OK){
            String resultado = data.getExtras().getString("resultado");
            //Coloque no EditText
            Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
        }

        else if(resultCode == 2)
            Toast.makeText(this, "Voltou do favorite", Toast.LENGTH_SHORT).show();

        createRecyclerView();

    }

    //Método de teste...
    public List<Vaga> gera(){
        List<Vaga> vagas = new ArrayList<>();

        VagaDAO dao = new VagaDAO(getApplicationContext());

        vagas = dao.getAll();

        RequestURL req = new RequestURL(this);
        VagasJSON vagasJSON = req.requestData("http://192.168.2.103:10555/vagas");
        for (Vaga v : vagasJSON.getVagas())
            Log.i("Vaga: ", v.getTitulo());

        Vaga vaga = new Vaga();
        vaga.setCidade("Barbacena");
        vaga.setDescricao("Caixa");
        vaga.setEmpresa("Bahamas");
        vaga.setEndereco("Rua ame");
        vaga.setFuncao("Caixa mesmo");
        vaga.setTitulo("Bah caixa");
        vaga.setSalario("123.98");
        vaga.setUrl_sine("soiofioiof");
        vagas.add(vaga);

        vaga = new Vaga();
        vaga.setCidade("Barbacena");
        vaga.setDescricao("Caixa");
        vaga.setEmpresa("Bahamas");
        vaga.setEndereco("Rua ame");
        vaga.setFuncao("Caixa mesmo");
        vaga.setTitulo("Bah caixa");
        vaga.setSalario("123.98");
        vaga.setUrl_sine("soiofioiof");
        vagas.add(vaga);

        vaga = new Vaga();
        vaga.setCidade("Barbacena");
        vaga.setDescricao("Caixa");
        vaga.setEmpresa("Bahamas");
        vaga.setEndereco("Rua ame");
        vaga.setFuncao("Caixa mesmo");
        vaga.setTitulo("Bah caixa");
        vaga.setSalario("123.98");
        vaga.setUrl_sine("soiofioiof");
        vagas.add(vaga);

        return vagas;
    }


    // Abre a intente de favoritos.
    public void favoriteOpenClick(View view) {

        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivityForResult(intent, 2);


    }

}
