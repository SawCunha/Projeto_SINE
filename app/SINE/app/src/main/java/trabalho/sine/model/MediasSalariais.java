package trabalho.sine.model;

/**
 * @version 0.2
 *          Created by Samuel Cunha on 11/02/2018.
 */
public class MediasSalariais {

    private Salarios salarios;
    private String nome_funcao;
    private String descricao_funcao;

    public MediasSalariais() {
    }

    public MediasSalariais(Salarios salarios, String nome_funcao, String descricao_funcao) {
        this.salarios = salarios;
        this.nome_funcao = nome_funcao;
        this.descricao_funcao = descricao_funcao;
    }

    public Salarios getSalarios() {
        return salarios;
    }

    public String getNome_funcao() {
        return nome_funcao;
    }

    public String getDescricao_funcao() {
        return descricao_funcao;
    }

    @Override
    public String toString() {
        return "MediasSalariais{" +
                "salarios=" + salarios +
                ", nome_funcao='" + nome_funcao + '\'' +
                ", descricao_funcao='" + descricao_funcao + '\'' +
                '}';
    }
}
