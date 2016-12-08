package trabalho.sine.model;

/**
 * Created by saw on 08/12/16.
 */

public class Emprego extends Object {

    private String nome;
    private String descricao;
    private String endereco;
    private Boolean favorite;

    public Emprego() {}

    public Emprego(String nome, String descricao, String endereco, Boolean favorite) {
        this.nome = nome;
        this.descricao = descricao;
        this.endereco = endereco;
        this.favorite = favorite;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "Empregos{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
