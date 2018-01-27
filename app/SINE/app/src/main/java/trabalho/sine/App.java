package trabalho.sine;

import android.app.Application;

import com.android.volley.VolleyError;

import trabalho.sine.controller.RequestURL;
import trabalho.sine.utils.Constantes;

/**
 * @version 0.1
 *          Created by Samuel Cunha on 27/01/18.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RequestURL requestURL = new RequestURL(this);
        requestURL.requestURL(Constantes.URL_API, new RequestURL.VolleyCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }
}
