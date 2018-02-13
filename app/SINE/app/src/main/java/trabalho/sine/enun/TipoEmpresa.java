package trabalho.sine.enun;

/**
 * Define os tipos de empresas que o usuário poderá escolher na pesquisa pelas margens
 * saláriais.
 */
public enum TipoEmpresa {

    PEQUENA("pequena_empresa"), MEDIA("media_empresa"), GRANDE("grande_empresa");

    private final String porteEmpresa;

    TipoEmpresa(String porteEmpresa) {
        this.porteEmpresa = porteEmpresa;
    }

    @Override
    public String toString() {
        return porteEmpresa;
    }
}
