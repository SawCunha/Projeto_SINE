package trabalho.sine.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import trabalho.sine.model.Cidade;
import trabalho.sine.persistence.DatabaseHelperCidadesEstados;

/**
 * Created by wagner on 09/12/16.
 */

public class CidadeDAO {
    private SQLiteDatabase sqLiteDatabase;
    private DatabaseHelperCidadesEstados helperCidadesEstados;
    private String[] columns = {DatabaseHelperCidadesEstados.COMUMN_ID,
                                DatabaseHelperCidadesEstados.COLUMN_NOME,
                                DatabaseHelperCidadesEstados.COLUMN_ESTADO_CIDADE,
                                DatabaseHelperCidadesEstados.COLUMN_CAPITAL_CIDADE};

    public CidadeDAO(Context context) {
        helperCidadesEstados = new DatabaseHelperCidadesEstados(context);

        try {
            helperCidadesEstados.createDatabase();
            helperCidadesEstados.openDatabase();
        } catch (IOException | SQLException e) {
            throw new Error("Unable to access database.");
        }
    }

    public List<Cidade> searchCities(String city, int limite){
        sqLiteDatabase = helperCidadesEstados.getReadableDatabase();
        String selection = "nome = ?";
        String[] selectionArgs = { city };
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = limite + "";

        List<Cidade> cidades = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(DatabaseHelperCidadesEstados.TABLE_CIDADES, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        if (cursor.moveToFirst()){
            do{
                Cidade cidade = new Cidade();
                cidade.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelperCidadesEstados.COMUMN_ID)));
                cidade.setNome(cursor.getString(cursor.getColumnIndex(DatabaseHelperCidadesEstados.COLUMN_NOME)));
                cidade.setEstado(cursor.getString(cursor.getColumnIndex(DatabaseHelperCidadesEstados.COLUMN_ESTADO_CIDADE)));
                cidade.setCapital(cursor.getInt(cursor.getColumnIndex(DatabaseHelperCidadesEstados.COLUMN_CAPITAL_CIDADE)));

                cidades.add(cidade);
            } while (cursor.moveToFirst());
        }
        sqLiteDatabase.close();
        return cidades;
    }

    public void close(){
        helperCidadesEstados.close();
    }
}
