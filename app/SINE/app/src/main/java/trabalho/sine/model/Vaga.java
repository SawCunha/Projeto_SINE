package trabalho.sine.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "vagas")
public class Vaga implements Comparable<Vaga> {
    @DatabaseField(id = true)
    private Long id;
    @DatabaseField
    private String titulo;
    @DatabaseField
    private String descricao;
    @DatabaseField
    private String endereco;
    @DatabaseField
    private String cidade;
    @DatabaseField
    private String funcao;
    @DatabaseField
    private String salario;
    @DatabaseField
    private String empresa;
    @DatabaseField
    private String url_sine;


    @DatabaseField
    private boolean favoritado = false;

    public boolean isFavoritado() {
        return favoritado;
    }

    public void setFavoritado(boolean favoritado) {
        this.favoritado = favoritado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getFuncao() {
        return funcao;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario() {
        this.salario = "R$0,00";
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getUrl_sine() {
        return url_sine;
    }

    @Override
    public int compareTo(@NonNull Vaga vaga) {
        try {
            if (this.getSalario() == null)
                this.salario = "R$0,00";
            if (vaga.getSalario() == null)
                vaga.setSalario();

            String sal = this.salario,
                    sal2 = vaga.getSalario();

            sal = sal.replaceAll("R\\$", "");
            sal2 = sal2.replaceAll("R\\$", "");
            sal = sal.replaceAll(",00", "");
            sal2 = sal2.replaceAll(",00", "");

            Log.d("Foi", sal);

            if (Float.parseFloat(sal) > Float.parseFloat(sal2))
                return -1;
            else if (Float.parseFloat(sal) < Float.parseFloat(sal2))
                return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }
}
