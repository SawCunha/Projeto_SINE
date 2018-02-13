package trabalho.sine.model;

/**
 * @version 0.1
 *          Created by Samuel Cunha on 13/02/2018.
 */
public class MediaSalariaJSON {

    private MediasSalariais media_salarial;
    private Integer error;

    public MediaSalariaJSON() {
    }

    public MediaSalariaJSON(MediasSalariais media_salarial, Integer error) {
        this.media_salarial = media_salarial;
        this.error = error;
    }

    public MediasSalariais getMedia_salarial() {
        return media_salarial;
    }

    public Integer getError() {
        return error;
    }

    @Override
    public String toString() {
        return "MediaSalariaJSON{" +
                "media_salarial=" + media_salarial +
                ", error=" + error +
                '}';
    }
}
