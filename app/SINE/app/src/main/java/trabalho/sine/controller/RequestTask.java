package trabalho.sine.controller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class RequestTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        String response = makeRequest(params[0]);
        return response;
    }//doInBackground()

    private String makeRequest(String urlAddress) {
        HttpURLConnection con = null;
        URL url = null;
        String response = null;

        try {
            url = new URL(urlAddress);
            con = (HttpURLConnection) url.openConnection();

            response = readStream(con.getInputStream());
        } catch (IOException e){
            Log.e("Error conection: ", e.getMessage());
        } finally {
            con.disconnect();
        }
        return response;
    }//makeRequest()

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
        } catch (IOException e) {
            Log.e("Error reading: ", e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("Error buffering: ", e.getMessage());
                }
            }
        }
        return builder.toString();
    }//readStream()
}//class RequestTask
