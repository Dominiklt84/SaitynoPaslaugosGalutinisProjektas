package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents movie rating.
 */
@Entity
@Table(name = "rating")
public class Rating extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private String source;

    private String value;

    public Rating() {
    }

    public Rating(Movie movie, String source, String value) {
        this.movie = movie;
        this.source = source;
        this.value = value;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getSource() {
        return source;
    }

    public String getValue() {
        return value;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setValue(String value) {
        this.value = value;
    }
}