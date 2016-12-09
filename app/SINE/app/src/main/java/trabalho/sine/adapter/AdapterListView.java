package trabalho.sine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import trabalho.sine.R;
import trabalho.sine.model.Vaga;

/**
 * Created by saw on 08/12/16.
 */

public class AdapterListView extends RecyclerView.Adapter<AdapterListView.DataObjectHolder> {

    //Cria uma TAG para o logs
    private static String LOG_TAG = "MyRecyclerViewAdapter";

    //Objeto com os Dados as serem exebidos na tela
    private ArrayList<Vaga> mDataset;

    private static Context context;

    //Objeto responsavel pelo Click no item do listview.
    private static MyClickListener myClickListener;

    //Construtor da Class
    public AdapterListView(ArrayList<Vaga> mDataset, Context context) {
        this.context = context;
        this.mDataset = mDataset;
    }


    //Class Interna responsavel por criar cada Item do ListView
    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView empregoNome;
        TextView empregoDescricao;
        TextView empregoEndereco;
        ImageButton favoriteBtn;
        private Boolean favoritado = false;

        //Construtor da Class
        public DataObjectHolder(View itemView) {
            super(itemView);
            empregoNome = (TextView) itemView.findViewById(R.id.emprego_nome);
            empregoDescricao = (TextView) itemView.findViewById(R.id.emprego_descricao);
            empregoEndereco = (TextView) itemView.findViewById(R.id.emprego_endereco);
            favoriteBtn = (ImageButton) itemView.findViewById(R.id.favorito);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        //Método responsavel pelo Click.
        @Override
        public void onClick(View v) {
            Log.i(LOG_TAG, "Clicado");
        }

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_adapter, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.empregoNome.setText(mDataset.get(position).getTitulo());
        holder.empregoDescricao.setText(mDataset.get(position).getDescricao());
        holder.empregoEndereco.setText(mDataset.get(position).getCidade());
        holder.favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vaga vaga = getItemPosition(position);
                if(vaga.getId() == null){
                    vaga.setId(1L);
                    holder.favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_black);
                }else{
                    vaga.setId(null);
                    holder.favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_border_black_48dp);
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

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
