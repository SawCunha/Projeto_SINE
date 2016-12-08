package trabalho.sine.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wagner on 08/12/16.
 */

public class CreateDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sineinfo.db";
    private static final int DATABASE_VERSION = 1;

    public CreateDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
