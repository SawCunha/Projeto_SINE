package trabalho.sine.dao;

import android.content.Context;

import trabalho.sine.model.Vaga;

/**
 * Created by wagner on 08/12/16.
 */

public class VagaDAO extends GenericDAO<Vaga> {
    public VagaDAO(Context context) {
        super(context, Vaga.class);
    }
}
