package trabalho.sine.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import trabalho.sine.controller.RequestTask;
import trabalho.sine.model.Cargo;
import trabalho.sine.model.CargoJSON;

/**
 * Created by wagner on 15/12/16.
 */

public class CargoSuggestionAdapter extends ArrayAdapter<Cargo> {
    protected static final String TAG = "CargoSuggestionAdapter";
    private List<Cargo> suggestions;
    private final String ENDERECO;

    public CargoSuggestionAdapter(Context context, String nameFilter, String url) {
        super(context, android.R.layout.simple_dropdown_item_1line);
        this.suggestions = new ArrayList<Cargo>();
        this.ENDERECO = url;
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public Cargo getItem(int index) {
        return suggestions.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                RequestTask requestTask = new RequestTask();

                if (constraint != null) {

                    try {
                        String response = requestTask.execute(ENDERECO + constraint.toString()).get();
                        Gson gson = new Gson();
                        CargoJSON cargoJSON = gson.fromJson(response, CargoJSON.class);

                        suggestions.clear();
                        suggestions = cargoJSON.getFuncoes();

                        filterResults.values = suggestions;
                        filterResults.count = suggestions.size();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }
}
