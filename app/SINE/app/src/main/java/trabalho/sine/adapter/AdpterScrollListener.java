package trabalho.sine.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.List;

import trabalho.sine.controller.RequestURL;
import trabalho.sine.dao.VagaDAO;
import trabalho.sine.model.Vaga;
import trabalho.sine.model.VagasJSON;
import trabalho.sine.utils.Constantes;

/**
 * @version 0.2
 *          Created by Samuel Cunha on 15/12/16.
 */

public class AdpterScrollListener extends RecyclerView.OnScrollListener {

    private final Context context;
    private final RecyclerView mRecyclerView;
    private final JobAdapter mAdapter;
    private final RecyclerView.LayoutManager mLayoutManager;
    private final Long cidadeEstado;
    private final Long funcao;
    private final int filtroIndex;
    private int totalItemCount;
    private int pos;

    public AdpterScrollListener(Context context, RecyclerView mRecyclerView, JobAdapter mAdapter,
                                RecyclerView.LayoutManager mLayoutManager, Long cidadeEstado, Long funcao,
                                int filtroIndex, int pos) {
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        this.mAdapter = mAdapter;
        this.mLayoutManager = mLayoutManager;
        this.cidadeEstado = cidadeEstado;
        this.funcao = funcao;
        this.filtroIndex = filtroIndex;
        this.pos = pos;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) {

            //Obtem as informações referente aos itens do RecyclerView.
            int pastVisiblesItems, visibleItemCount;
            visibleItemCount = mLayoutManager.getChildCount();
            totalItemCount = mLayoutManager.getItemCount();
            pastVisiblesItems = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
            //Verifica se chegou no ultimo elemento do recyclerview.
            if ((visibleItemCount + pastVisiblesItems) == totalItemCount) {
                pos++;
                RequestURL req = new RequestURL(context);
                totalItemCount--;
                mRecyclerView.scrollToPosition(totalItemCount);
                req.requestURL(String.format(Constantes.URL_API + Constantes.URL_API_VAGAS,
                        funcao, cidadeEstado, pos, filtroIndex), new RequestURL.VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        int position = totalItemCount;
                        int positionRemove = position++;
                        mAdapter.addItem(null, positionRemove);
                        Gson gson = new Gson();
                        VagasJSON vagasJSON = gson.fromJson(response, VagasJSON.class);

                        VagaDAO vagaDAO = new VagaDAO(context);
                        List<Vaga> vagasBd = vagaDAO.getAll();

                        for (Vaga v : vagasJSON.getVagas()) {
                            for (Vaga vs : vagasBd) {
                                if (vs.getId().toString().equalsIgnoreCase(v.getId().toString()))
                                    v.setFavoritado(true);
                            }
                            mAdapter.addItem(v, position++);
                        }

                        mAdapter.getVagas().remove(positionRemove);
                        mAdapter.removeItem(positionRemove);
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            Log.e("Error", error.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }
    }
}
