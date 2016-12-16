package trabalho.sine.model;

public class MediaSalarial {
    private Salarios salarios;
    private String nome_funcao;
    private String descricao_funcao;

    public Salarios getSalarios() {
        return salarios;
    }

    public void setSalarios(Salarios salarios) {
        this.salarios = salarios;
    }

    public String getNome_funcao() {
        return nome_funcao;
    }

    public void setNome_funcao(String nome_funcao) {
        this.nome_funcao = nome_funcao;
    }

    public String getDescricao_funcao() {
        return descricao_funcao;
    }

    public void setDescricao_funcao(String descricao_funcao) {
        this.descricao_funcao = descricao_funcao;
    }
}
