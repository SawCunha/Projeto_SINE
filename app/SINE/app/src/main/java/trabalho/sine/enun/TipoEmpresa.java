package trabalho.sine.enun;

/**
 * Define os tipos de empresas que o usuário poderá escolher na pesquisa pelas margens
 * saláriais.
 */
public enum TipoEmpresa {

    PEQUENA("Pequeno Porte"), MEDIA("Médio Porte"), GRANDE("Grande Porte");

    private String porteEmpresa;

    TipoEmpresa(String porteEmpresa) {
        this.porteEmpresa = porteEmpresa;
    }

    @Override
    public String toString() {
        return porteEmpresa;
    }
}
