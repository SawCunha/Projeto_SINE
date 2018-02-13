package trabalho.sine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import trabalho.sine.R;

/**
 * @version 0.1
 *          Created by Samuel Cunha on 27/11/17.
 */
class JobLoadHolder extends RecyclerView.ViewHolder {

    public final ProgressBar progressBar;

    public JobLoadHolder(View view) {
        super(view);
        progressBar = view.findViewById(R.id.loading);
    }
}
