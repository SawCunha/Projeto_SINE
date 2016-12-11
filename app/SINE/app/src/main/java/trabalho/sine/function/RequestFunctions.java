package trabalho.sine.function;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import trabalho.sine.controller.RequestURL;
import trabalho.sine.dao.VagasJSON;
import trabalho.sine.model.Vaga;

/**
 * Created by saw on 11/12/16.
 */

public class RequestFunctions {

    private static final String URL = "http://192.168.0.106:10555/";
    private static final String VAGAS_ALL = "vagas";
    private static List<Vaga> vagas;

    //Método para obter todas as Vagas.
    public static List<Vaga> obtemVagas(final Context context){

        RequestURL req = new RequestURL(context);
        vagas = new ArrayList<>();
        //Testa a requisição.
        req.requestURL(URL+VAGAS_ALL, new RequestURL.VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                VagasJSON vagasJSON = gson.fromJson(response, VagasJSON.class);
                vagas = vagasJSON.getVagas();
            }
        });

        while(vagas.isEmpty());

        return vagas;

    }

}
