package trabalho.sine.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import trabalho.sine.model.Vaga;

/**
 * Created by wagner on 08/12/16.
 */

public class VagaDAO extends BaseDaoImpl<Vaga, Long> {
    public VagaDAO(ConnectionSource connectionSource) throws SQLException {
        super(Vaga.class);
        setConnectionSource(connectionSource);
        initialize();
    }
}
