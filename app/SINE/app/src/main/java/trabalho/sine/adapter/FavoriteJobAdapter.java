package trabalho.sine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.UnsupportedEncodingException;
import java.util.List;

import trabalho.sine.R;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.model.Vaga;

/**
 * @version 0.1
 *          Created by Samuel Cunha on 25/11/17.
 */
public class FavoriteJobAdapter extends RecyclerView.Adapter {

    //Cria uma TAG para o logs
    private static String LOG_TAG = "MyRecyclerViewAdapter";

    //Objeto com os Dados as serem exebidos na tela
    private final List<Vaga> vagas;

    private final Context context;

    //Construtor da Class
    public FavoriteJobAdapter(List<Vaga> vagas, Context context) {
        this.context = context;
        this.vagas = vagas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_adapter, parent, false);
        return new JobViewHolder(view, context);
    }

    private Vaga getItemPosition(int position) {
        return vagas.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final JobViewHolder viewHolder = (JobViewHolder) holder;
        viewHolder.setPosition(position);
        viewHolder.setVaga(getItemPosition(position));
        try {
            viewHolder.vagaNome.setText(new String(vagas.get(position).getFuncao().
                    getBytes(), "UTF-8"));
            viewHolder.vagaEmpresa.setText(vagas.get(position).getEmpresa());
            viewHolder.vagaEndereco.setText(vagas.get(position).getCidade());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        viewHolder.favoriteBtn.setBackgroundResource(
                (!vagas.get(position).isFavoritado() ?
                        R.drawable.favorite_border : R.drawable.favorite_black));

        viewHolder.favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vaga vaga = getItemPosition(position);
                VagaDAO vagaDAO = new VagaDAO(context.getApplicationContext());

                if (!vaga.isFavoritado()) {
                    vaga.setFavoritado(true);
                    vagaDAO.insert(vaga);
                    viewHolder.favoriteBtn.setBackgroundResource(R.drawable.favorite_black);
                } else {
                    vagaDAO.delete(vaga);
                    vaga.setFavoritado(false);
                    viewHolder.favoriteBtn.setBackgroundResource(R.drawable.favorite_border);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return vagas.size();
    }
}
