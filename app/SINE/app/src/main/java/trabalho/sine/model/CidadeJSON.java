package trabalho.sine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wagner on 15/12/16.
 */

public class CidadeJSON {
    private Long error;
    private List<Cidade> cidades = new ArrayList<>();

    public List<Cidade> getCidades() {
        return cidades;
    }

}
