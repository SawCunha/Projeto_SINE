package trabalho.sine.model;

public class Salarios {
    private FaixaSalarial pequena_empresa;
    private FaixaSalarial media_empresa;
    private FaixaSalarial grande_empresa;

    public FaixaSalarial getPequena_empresa() {
        return pequena_empresa;
    }

    public void setPequena_empresa(FaixaSalarial pequena_empresa) {
        this.pequena_empresa = pequena_empresa;
    }

    public FaixaSalarial getMedia_empresa() {
        return media_empresa;
    }

    public void setMedia_empresa(FaixaSalarial media_empresa) {
        this.media_empresa = media_empresa;
    }

    public FaixaSalarial getGrande_empresa() {
        return grande_empresa;
    }

    public void setGrande_empresa(FaixaSalarial grande_empresa) {
        this.grande_empresa = grande_empresa;
    }
}
