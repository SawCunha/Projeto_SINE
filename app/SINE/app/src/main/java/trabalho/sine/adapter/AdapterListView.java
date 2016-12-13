package trabalho.sine.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import trabalho.sine.R;
import trabalho.sine.ResultActivity;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.model.Vaga;

/**
 * Created by saw on 08/12/16.
 */

public class AdapterListView extends RecyclerView.Adapter<AdapterListView.DataObjectHolder> {

    //Cria uma TAG para o logs
    private static String LOG_TAG = "MyRecyclerViewAdapter";

    //Objeto com os Dados as serem exebidos na tela
    private List<Vaga> mDataset;

    private static Context context;

    //Construtor da Class
    public AdapterListView(List<Vaga> mDataset, Context context) {
        this.context = context;
        this.mDataset = mDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_adapter, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    //Define O Text do Labels e a Imagem de favorito.
    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {


        holder.setVaga(mDataset.get(position));
        holder.vagaNome.setText(mDataset.get(position).getFuncao());
        holder.vagaEmpresa.setText(mDataset.get(position).getEmpresa());
        holder.vagaEndereco.setText(mDataset.get(position).getCidade());
        holder.favoriteBtn.setBackgroundResource(
                (mDataset.get(position).isFavoritado() == false ?
                        R.drawable.ic_favorite_border_black_48dp:R.drawable.ic_favorite_black));

        holder.favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vaga vaga = getItemPosition(position);
                VagaDAO vagaDAO = new VagaDAO(context.getApplicationContext());
                if(vaga.isFavoritado() == false){
                    vaga.setFavoritado(true);
                    vagaDAO.insert(vaga);
                    holder.favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_black);
                    Toast.makeText(context,R.string.toast_msg_adapter_list_favoritado,Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("id_adap", vaga.getId().toString());
                    vagaDAO.delete(vaga);
                    vaga.setFavoritado(false);
                    holder.favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_border_black_48dp);
                    Toast.makeText(context,R.string.toast_msg_adapter_list_desfavoritado,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addItem(Vaga dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    public Vaga getItemPosition(int position){
        return mDataset.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    //Class Interna responsavel por criar cada Item do ListView
    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView vagaNome;
        TextView vagaEmpresa;
        TextView vagaEndereco;
        ImageButton favoriteBtn;
        private Vaga vaga;
        static final int ACTIVITY_REQUEST = 1;

        //Construtor da Class
        //Obtem as Referencias para Views que será utilizada.
        public DataObjectHolder(View itemView) {
            super(itemView);
            vagaNome = (TextView) itemView.findViewById(R.id.vaga_nome);
            vagaEmpresa = (TextView) itemView.findViewById(R.id.vaga_empresa);
            vagaEndereco = (TextView) itemView.findViewById(R.id.vaga_endereco);
            favoriteBtn = (ImageButton) itemView.findViewById(R.id.favorito);
            itemView.setOnClickListener(this);
        }

        //Método responsavel pelo Click.
        @Override
        public void onClick(View v) {
            Log.i(LOG_TAG, v.getScrollY()+"");
            Intent resultadoActivity = new Intent(context, ResultActivity.class);
            resultadoActivity.putExtra("vaga",transformaVagaJson(vaga));
            ((Activity)context).startActivityForResult(resultadoActivity,ACTIVITY_REQUEST);
        }

        private String transformaVagaJson(Vaga vaga){
            Gson gson = new Gson();
            return gson.toJson(vaga);
        }

        public void setVaga(Vaga vaga) {
            this.vaga = vaga;
        }

    }


}
