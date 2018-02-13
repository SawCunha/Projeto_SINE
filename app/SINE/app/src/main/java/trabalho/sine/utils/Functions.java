package trabalho.sine.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Samuel Cunha on 27/11/17.
 */
public class Functions {

    /**
     * método responsavel por fechar a aplicação.
     *
     * @param context Contexto atual da Aplicação.
     */
    public static void ClosseApp(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static void DialogErro(final Activity context, String msg) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Erro");
        builder.setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        context.finish();
                        Log.d("Ok", "");
                    }
                });

        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }
}
