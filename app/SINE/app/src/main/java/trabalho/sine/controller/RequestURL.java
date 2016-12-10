package trabalho.sine.controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import trabalho.sine.dao.VagasJSON;
import trabalho.sine.interfaces.VolleyCallback;
import trabalho.sine.model.Vaga;

public class RequestURL {
    private final Context context;
    private VagasJSON vagasJSON = new VagasJSON();

    public RequestURL(Context context) {
        this.context = context;
    }

    public VagasJSON requestData(String url){
        request(url, new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                vagasJSON = gson.fromJson(response, VagasJSON.class);
                for (Vaga v : vagasJSON.getVagas())
                    Log.i("vaga: ", v.getTitulo());
            }
        });
        return vagasJSON;
    }

    //Efetua a requisição dos dados através da url recebida pelo usuário.
    private void request(String url, final VolleyCallback callback) {

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error Request: ", error.getMessage());
            }
        });
        queue.add(stringRequest);
    }//doInBackground()
}//class RequestURL
