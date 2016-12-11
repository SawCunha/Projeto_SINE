package trabalho.sine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Define o modelo com a estrutura obtida através da requisição http. Isso é necessário para que a API
 * do Gson possa fazer o parser do objeto de forma correta.
 */

public class VagasJSON {
    private int error;
    private List<Vaga> vagas = new ArrayList<>();

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<Vaga> getVagas() {
        return vagas;
    }

    public void setVagas(List<Vaga> vagas) {
        this.vagas = vagas;
    }
}
