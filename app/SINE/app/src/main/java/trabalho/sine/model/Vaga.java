package trabalho.sine.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "vaga")
public class Vaga {
    @DatabaseField(generatedId = true) private Long id;
    @DatabaseField private String vaga;
    @DatabaseField private String descricao;
    @DatabaseField private String cidade;
    @DatabaseField private String estado;
    @DatabaseField private String telefone;

    public Vaga() {}

    public Vaga(String vaga, String descricao, String cidade, String estado, String telefone) {
        this.vaga = vaga;
        this.descricao = descricao;
        this.cidade = cidade;
        this.estado = estado;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVaga() {
        return vaga;
    }

    public void setVaga(String vaga) {
        this.vaga = vaga;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
