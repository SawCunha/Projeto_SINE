package trabalho.sine.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import trabalho.sine.R;
import trabalho.sine.model.Emprego;

/**
 * Created by saw on 08/12/16.
 */

public class AdapterListView extends RecyclerView.Adapter<AdapterListView.DataObjectHolder> {

    //Cria uma TAG para o logs
    private static String LOG_TAG = "MyRecyclerViewAdapter";

    //Objeto com os Dados as serem exebidos na tela
    private ArrayList<Emprego> mDataset;

    //Objeto responsavel pelo Click no item do listview.
    private static MyClickListener myClickListener;

    //Construtor da Class
    public AdapterListView(ArrayList<Emprego> mDataset) {
        this.mDataset = mDataset;
    }


    //Class Interna responsavel por criar cada Item do ListView
    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView empregoNome;
        TextView empregoDescricao;
        TextView empregoEndereco;

        //Construtor da Class
        public DataObjectHolder(View itemView) {
            super(itemView);
            empregoNome = (TextView) itemView.findViewById(R.id.emprego_nome);
            empregoDescricao = (TextView) itemView.findViewById(R.id.emprego_descricao);
            empregoEndereco = (TextView) itemView.findViewById(R.id.emprego_endereco);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        //Método responsavel pelo Click.
        @Override
        public void onClick(View v) {
            Log.i(LOG_TAG, "Clicado");
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    //Define o método que ficara esperando o click do Usuario no Item do ListView
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }


    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_adapter, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.empregoNome.setText(mDataset.get(position).getNome());
        holder.empregoDescricao.setText(mDataset.get(position).getDescricao());
        holder.empregoEndereco.setText(mDataset.get(position).getEndereco());
    }

    public void addItem(Emprego dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
