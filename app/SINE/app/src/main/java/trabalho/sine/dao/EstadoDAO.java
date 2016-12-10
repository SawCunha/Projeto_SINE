package trabalho.sine.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import trabalho.sine.model.Estado;
import trabalho.sine.persistence.DatabaseHelperCidadesEstados;

/**
 * Created by wagner on 10/12/16.
 */

public class EstadoDAO {
    private SQLiteDatabase sqLiteDatabase;
    private DatabaseHelperCidadesEstados helperCidadesEstados;
    private String[] columns = {DatabaseHelperCidadesEstados.COMUMN_ID,
            DatabaseHelperCidadesEstados.COLUMN_NOME,
            DatabaseHelperCidadesEstados.COLUMN_STATUS_ESTADO,
            DatabaseHelperCidadesEstados.COLUMN_SIGLA_ESTADO};

    public EstadoDAO(Context context) {
        helperCidadesEstados = new DatabaseHelperCidadesEstados(context);

        try {
            helperCidadesEstados.createDatabase();
            helperCidadesEstados.openDatabase();
        } catch (IOException | SQLException e) {
            throw new Error("Unable to access database.");
        }
    }

    public List<Estado> searchStates(String state){
        sqLiteDatabase = helperCidadesEstados.getReadableDatabase();
        List<Estado> estados = new ArrayList<>();

        String selection = "nome like ? COLLATE NOCASE ";
        String[] selectionArgs = { state + "%" };
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        Cursor cursor = sqLiteDatabase.query(DatabaseHelperCidadesEstados.TABLE_ESTADOS, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        if (cursor.moveToFirst()){
            do{
                Estado estado = new Estado();
                estado.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelperCidadesEstados.COMUMN_ID)));
                estado.setNome(cursor.getString(cursor.getColumnIndex(DatabaseHelperCidadesEstados.COLUMN_NOME)));
                estado.setStatus(cursor.getInt(cursor.getColumnIndex(DatabaseHelperCidadesEstados.COLUMN_STATUS_ESTADO)));
                estado.setSigla(cursor.getString(cursor.getColumnIndex(DatabaseHelperCidadesEstados.COLUMN_SIGLA_ESTADO)));
                Log.i("Estado passando: ", estado.getNome());
                estados.add(estado);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return estados;
    }

    public void close(){
        helperCidadesEstados.close();
    }
}
