package trabalho.sine.model;

/**
 * @version 0.1
 *          Created by Samuel Cunha on 13/02/2018.
 */
public class Salarios {

    private MediaSalarial pequena_empresa;
    private MediaSalarial media_empresa;
    private MediaSalarial grande_empresa;

    public Salarios() {
    }

    public Salarios(MediaSalarial pequena_empresa, MediaSalarial media_empresa, MediaSalarial grande_empresa) {
        this.pequena_empresa = pequena_empresa;
        this.media_empresa = media_empresa;
        this.grande_empresa = grande_empresa;
    }

    public MediaSalarial getPequena_empresa() {
        return pequena_empresa;
    }

    public MediaSalarial getMedia_empresa() {
        return media_empresa;
    }

    public MediaSalarial getGrande_empresa() {
        return grande_empresa;
    }

    @Override
    public String toString() {
        return "Salarios{" +
                "pequena_empresa=" + pequena_empresa +
                ", media_empresa=" + media_empresa +
                ", grande_empresa=" + grande_empresa +
                '}';
    }
}
