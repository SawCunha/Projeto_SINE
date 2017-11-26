package trabalho.sine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.List;

import trabalho.sine.R;
import trabalho.sine.controller.RequestURL;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.model.Vaga;
import trabalho.sine.utils.Constantes;

/**
 * Created by Samuel Cunha on 25/11/17.
 */
public class JobAdapter extends RecyclerView.Adapter{

    //Cria uma TAG para o logs
    private static String LOG_TAG = "MyRecyclerViewAdapter";

    //Objeto com os Dados as serem exebidos na tela
    private static List<Vaga> vagas;

    private static Context context;

    //Construtor da Class
    public JobAdapter(List<Vaga> vagas, Context context) {
        this.context = context;
        this.vagas = vagas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_adapter, parent, false);
        return new JobViewHolder(view,context);
    }

    public void addItem(Vaga dataObj, int index) {
        vagas.add(dataObj);
        vagas.add(dataObj);
        notifyItemInserted(index);
        notifyDataSetChanged();
    }

    public Vaga getItemPosition(int position){
        return vagas.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final JobViewHolder viewHolder = (JobViewHolder) holder;
        viewHolder.setPosition(position);
        viewHolder.setVaga(getItemPosition(position));
        try {
            viewHolder.vagaNome.setText(new String(vagas.get(position).getFuncao().
                    getBytes(),"UTF-8"));
            viewHolder.vagaEmpresa.setText(vagas.get(position).getEmpresa());
            viewHolder.vagaEndereco.setText(vagas.get(position).getCidade());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        viewHolder.favoriteBtn.setBackgroundResource(
                (vagas.get(position).isFavoritado() == false ?
                        R.drawable.favorite_border:R.drawable.favorite_black));

        viewHolder.favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Vaga vaga = getItemPosition(position);
                final VagaDAO vagaDAO = new VagaDAO(context.getApplicationContext());

                if(vaga.isFavoritado() == false){
                    RequestURL req = new RequestURL(context);
                    try {
                        req.requestURL(String.format(Constantes.URL_API + Constantes.URL_API_VAGA,
                                new String(vagas.get(position).getCidade().getBytes("UTF-8"),"UTF-8")
                                        .replace(" ","%20"),
                                new String(vagas.get(position).getFuncao().getBytes("UTF-8"),"UTF-8")
                                        .replace(" ","%20"),
                                vagas.get(position).getId()),
                                new RequestURL.VolleyCallback() {
                                    @Override
                                    public void onSuccess(String response) {
                                        Gson gson = new Gson();
                                        Vaga vagaJSON = gson.fromJson(response, Vaga.class);
                                        vagaJSON.setFavoritado(true);
                                        vagaDAO.insert(vagaJSON);
                                    }

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        try {
                                            vagaDAO.insert(vaga);
                                        }catch (Exception e){
                                            Log.e("Error",error.getMessage());
                                        }
                                    }
                                });
                        viewHolder.favoriteBtn.setBackgroundResource(R.drawable.favorite_black);
                        Toast.makeText(context,R.string.toast_msg_adapter_list_favoritado,Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }else{
                    vagaDAO.delete(vaga);
                    vaga.setFavoritado(false);
                    viewHolder.favoriteBtn.setBackgroundResource(R.drawable.favorite_border);
                    Toast.makeText(context,R.string.toast_msg_adapter_list_desfavoritado,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return vagas.size();
    }
}
