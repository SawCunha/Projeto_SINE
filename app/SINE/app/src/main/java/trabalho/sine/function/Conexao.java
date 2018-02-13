package trabalho.sine.function;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @version 0.2
 *          Created by Samuel Cunha on 11/12/16.
 */

public class Conexao {

    /**
     * Método que verifica se há uma conexão com o servidor.
     *
     * @param contexto recebe o contexto da activity atual.
     * @return um boolean com o status da conexão:
     * - true: A conexão com o servidor foi realizada com sucesso;
     * - false: Não há uma conexão com o servidor.
     */
    public static boolean isConectado(Context contexto) {
        ConnectivityManager cn = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cn != null;
        NetworkInfo infoRede = cn.getActiveNetworkInfo();

        return infoRede != null && infoRede.isConnected();
    }

}
