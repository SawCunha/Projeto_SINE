package trabalho.sine.utils;

public final class Constantes {

    public static final String URL_API = "https://sine-api-tsi.herokuapp.com";
    public static final String URL_API_VAGAS_GERAL = "https://sine-api-tsi.herokuapp.com/vagas";
    public static final String URL_API_VAGAS = "/vagas?idfuncao=%d&idcidade=%d&numPagina=%d&tipoOrdenacao=%d";
    public static final String URL_API_VAGA = "/vaga/%s/%s/%s";
    public static final String URL_API_CHARTS_VALUES = "https://sine-api-tsi.herokuapp.com/media-salarial?idfuncao=%d";
    private Constantes() {
    }
}
