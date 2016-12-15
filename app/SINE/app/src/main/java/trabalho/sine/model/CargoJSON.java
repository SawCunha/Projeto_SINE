package trabalho.sine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wagner on 15/12/16.
 */

public class CargoJSON {
    private Long error;
    private List<Cargo> funcoes = new ArrayList<>();

    public Long getError() {
        return error;
    }

    public void setError(Long error) {
        this.error = error;
    }

    public List<Cargo> getFuncoes() {
        return funcoes;
    }

    public void setFuncoes(List<Cargo> funcoes) {
        this.funcoes = funcoes;
    }
}
