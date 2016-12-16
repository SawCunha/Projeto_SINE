package trabalho.sine.controller;

import java.util.concurrent.ExecutionException;

import trabalho.sine.utils.Constantes;

/**
 * Created by wagner on 15/12/16.
 */

public class SearchAverageForGraphic {
    private static final String URL_SEARCH_FOR_AVERAGE = Constantes.URL_API + "/media-salarial?idfuncao=";
    private static final String URL_SEARCH_FUNCTION_ID = Constantes.URL_API + "/idfuncao/";
    private final String city;


    public SearchAverageForGraphic(String city) {
        this.city = city;
    }

    private String searchIdFunction() {
        RequestTask requestTask = new RequestTask();

        String response = null;
        try {
            response = requestTask.execute(URL_SEARCH_FUNCTION_ID + city).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return response;
    }
}
