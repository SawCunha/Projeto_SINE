package trabalho.sine.utils;

public final class Constantes {

    private Constantes(){}

    public static final String URL_API = "https://sine-api-tsi.herokuapp.com";
    public static final String URL_API_VAGAS = "https://sine-api-tsi.herokuapp.com/vagas";
    public static final String URL_API_VAGAS_ = "/vagas?idfuncao=%d&idcidade=%d&numPagina=%d&tipoOrdenacao=%d";
    public static final String URL_API_VAGA = "/vaga/%s/%s/%s";
}
