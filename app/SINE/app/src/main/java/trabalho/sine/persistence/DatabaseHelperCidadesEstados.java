package trabalho.sine.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Classe responsável por importar a base de dados fixa de consulta da aplicação que deverá
 * ser instalada junta da mesma.
 */

public class DatabaseHelperCidadesEstados extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/trabalho.sine/databases/";

    public static final String DATABASE_NAME = "cidades_estados.db";
    public static final String TABLE_CIDADES = "Cidades";
    public static final String TABLE_ESTADOS = "Estados";
    public static final String COMUMN_ID = "_id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_ESTADO_CIDADE = "estado";
    public static final String COLUMN_CAPITAL_CIDADE = "capital";
    public static final String COLUMN_STATUS_ESTADO = "status";
    public static final String COLUMN_SIGLA_ESTADO = "sigla";

    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase mySqLiteDatabase;
    private final Context context;

    public DatabaseHelperCidadesEstados(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();

        if (!dbExist) {
            this.getReadableDatabase();

            try {
                copyDatabase();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDatabase(){
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }

    private void copyDatabase() throws IOException {
        InputStream myInputStream = context.getAssets().open(DATABASE_NAME);

        String outFileName = DB_PATH + DATABASE_NAME;

        OutputStream myOutputStream = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInputStream.read(buffer)) > 0){
            myOutputStream.write(buffer, 0, length);
        }

        myOutputStream.flush();
        myOutputStream.close();
        myInputStream.close();
    }

    public void openDatabase() throws SQLException {
        String myPath = DB_PATH + DATABASE_NAME;
        mySqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close(){
        if(mySqLiteDatabase != null) mySqLiteDatabase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
