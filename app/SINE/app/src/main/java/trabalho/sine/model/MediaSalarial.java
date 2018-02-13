package trabalho.sine.model;

/**
 * @version 0.1
 *          Created by Samuel Cunha on 11/02/2018.
 */
public class MediaSalarial {

    private Float trainee;
    private Float junior;
    private Float pleno;
    private Float senior;
    private Float master;

    public MediaSalarial() {
    }

    public MediaSalarial(Float trainne, Float junior, Float pleno, Float senior, Float master) {
        this.trainee = trainne;
        this.junior = junior;
        this.pleno = pleno;
        this.senior = senior;
        this.master = master;
    }

    public Float getTrainne() {
        return trainee;
    }

    public Float getJunior() {
        return junior;
    }

    public Float getPleno() {
        return pleno;
    }

    public Float getSenior() {
        return senior;
    }

    public Float getMaster() {
        return master;
    }

    @Override
    public String toString() {
        return "MediaSalarial{" +
                "trainne=" + trainee +
                ", junior=" + junior +
                ", pleno=" + pleno +
                ", senior=" + senior +
                ", master=" + master +
                '}';
    }
}
