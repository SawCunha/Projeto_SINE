package trabalho.sine.model;

/**
 * Model utilizado para obter os dados do AutoComplete do Cargo.
 */

public class Cargo {
    private Long id;
    private String descricao;

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
