package trabalho.sine.function;

/**
 * Created by wagner on 15/12/16.
 */

public class ManipulaString {
    public static String removeParametro(String parametro, String original){
        return original.replaceAll(parametro, "");
    }
}
