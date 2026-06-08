package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * Entitity, saugantis filmo vertinimo informaciją.
 */
@Entity
@Table(name = "rating")
@AttributeOverride(
        name = "id",
        column = @Column(name = "rating_id")
)
public class Rating extends BaseEntity {
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private String source;

    @Column(name = "rating_value")
    private String value;

    public Rating() {
    }

    public Rating(Movie movie, String source, String value) {
        this.movie = movie;
        this.source = source;
        this.value = value;
    }

    /**
     * Grąžina filmą, kuriam priklauso vertinimas.
     *
     * @return filmas
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * Grąžina vertinimo šaltinį.
     *
     * @return vertinimo šaltinis
     */
    public String getSource() {
        return source;
    }

    /**
     * Grąžina vertinimo reikšmę.
     *
     * @return vertinimo reikšmė
     */
    public String getValue() {
        return value;
    }

    /**
     * Nustato filmą, kuriam priklauso vertinimas.
     *
     * @param movie filmas
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    /**
     * Nustato vertinimo šaltinį.
     *
     * @param source vertinimo šaltinis
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Nustato vertinimo reikšmę.
     *
     * @param value vertinimo reikšmė
     */
    public void setValue(String value) {
        this.value = value;
    }
}