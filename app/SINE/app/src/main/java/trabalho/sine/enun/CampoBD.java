package trabalho.sine.enun;

/**
 * Created by saw on 13/12/16.
 */

public enum CampoBD {
    ID("id"), SALARIO("salario");

    private final String campo;

    CampoBD(String campo) {
        this.campo = campo;
    }

    @Override
    public String toString() {
        return campo;
    }
}
