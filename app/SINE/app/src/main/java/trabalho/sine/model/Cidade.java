
package trabalho.sine.model;

/*
    Model utilizado para obter os dados que serão utilizados no AutoComplete.
 */
public class Cidade {
    private Long id;
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() { return descricao; }
}
