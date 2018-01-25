package trabalho.sine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wagner on 15/12/16.
 */

public class CidadeJSON {
    private Long error;
    private List<Cidade> cidades = new ArrayList<>();

    public Long getError() {
        return error;
    }

    public void setError(Long error) {
        this.error = error;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }
}
