package trabalho.sine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import trabalho.sine.adapter.AdapterListView;
import trabalho.sine.controller.RequestURL;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.interfaces.VolleyCallback;
import trabalho.sine.model.Emprego;
import trabalho.sine.model.Vaga;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "RecyclerViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_empregos);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterListView(gera(),this);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        testaVolley();
        testaBanco();
    }


    private void testaVolley(){

        RequestURL.requestURL("https://cinevertentes.herokuapp.com/api/v1/cineplaza/movies", this, new VolleyCallback() {

            @Override
            public void onSuccess(String response) {
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void testaBanco(){
        VagaDAO dao = new VagaDAO(getApplicationContext());
        Vaga vaga = new Vaga();

        vaga.setCidade("Barbacena");
        vaga.setDescricao("Caixa");
        vaga.setEmpresa("Bahamas");
        vaga.setEndereco("Rua ame");
        vaga.setFuncao("Caixa mesmo");
        vaga.setTitulo("Bah caixa");
        vaga.setSalario(123.98);
        vaga.setUrlSine("soiofioiof");
        dao.insert(vaga);

        List<Vaga> vagas = dao.getAll();

        for(Vaga v : vagas)
            Log.d("vaga: ", v.getTitulo());

        dao.delete(vagas.get(0).getId());

        vagas = dao.getAll();

        if (vagas.isEmpty()) Toast.makeText(this, "Não existe usuário cadastrado", Toast.LENGTH_SHORT).show();
    }

    //Método de teste...
    public ArrayList<Emprego> gera(){
        ArrayList<Emprego> empregos = new ArrayList<>();
        Emprego emprego = new Emprego("Teste 1","Descrição 1","Rio Grande do Sul",false);
        empregos.add(emprego);
        emprego = new Emprego("Teste 1","Descrição 1","Rio Grande do Sul",false);
        empregos.add(emprego);
        emprego = new Emprego("Teste 2","Descrição 2","Rio Grande do Sul",false);
        empregos.add(emprego);
        emprego = new Emprego("Teste 3","Descrição 3","Rio Grande do Sul",false);
        empregos.add(emprego);
        emprego = new Emprego("Teste 4","Descrição 4","Rio Grande do Sul",false);
        empregos.add(emprego);
        emprego = new Emprego("Teste 5","Descrição 5","Rio Grande do Sul",false);
        empregos.add(emprego);
        emprego = new Emprego("Teste 6","Descrição 6","Rio Grande do Sul",false);
        empregos.add(emprego);
        emprego = new Emprego("Teste 7","Descrição 7","Rio Grande do Sul",false);
        empregos.add(emprego);
        return  empregos;
    }

}
