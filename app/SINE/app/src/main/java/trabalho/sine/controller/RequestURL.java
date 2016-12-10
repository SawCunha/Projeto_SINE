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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import trabalho.sine.interfaces.VolleyCallback;
import trabalho.sine.model.Vaga;

public class RequestURL {
    private final Context context;
    private List<Vaga> eList = new ArrayList<>();

    public RequestURL(Context context) {
        this.context = context;
    }

    public List<Vaga> requestData(String url){
        request(url, new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                Type eListTyle = new TypeToken<ArrayList<Vaga>>(){}.getType();
                eList = gson.fromJson(response, eListTyle);
                Log.d("bell", eList.toString());
            }
        });
        return eList;
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
