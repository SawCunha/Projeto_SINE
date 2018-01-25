package trabalho.sine.controller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RequestURL {
    private final Context context;

    public RequestURL(Context context) {
        this.context = context;
    }

    //Efetua a requisição dos dados através da url recebida pelo usuário.
    public void requestURL(String url, final VolleyCallback callback) {

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
                callback.onErrorResponse(error);
            }
        });
        queue.add(stringRequest);
    }

    public interface VolleyCallback {
        void onSuccess(String response);
        void onErrorResponse(VolleyError error);
    }
}//class RequestURL


