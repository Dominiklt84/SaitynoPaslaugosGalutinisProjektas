package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entitity, saugantis informaciją apie filmo peržiūras.
 */
@Entity
@Table(name = "movie_view")
@AttributeOverride(
        name = "id",
        column = @Column(name = "movie_view_id")
)
public class MovieView extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private LocalDate viewDate;

    private Integer viewCount;

    public MovieView() {
    }

    public MovieView(Movie movie, LocalDate viewDate, Integer viewCount) {
        this.movie = movie;
        this.viewDate = viewDate;
        this.viewCount = viewCount;
    }

    /**
     * Grąžina filmą, kuriam priklauso peržiūros įrašas.
     *
     * @return filmas
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * Grąžina peržiūros datą.
     *
     * @return peržiūros data
     */
    public LocalDate getViewDate() {
        return viewDate;
    }

    /**
     * Grąžina peržiūrų skaičių.
     *
     * @return peržiūrų skaičius
     */
    public Integer getViewCount() {
        return viewCount;
    }

    /**
     * Nustato filmą, kuriam priklauso peržiūros įrašas.
     *
     * @param movie filmas
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    /**
     * Nustato peržiūros datą.
     *
     * @param viewDate peržiūros data
     */
    public void setViewDate(LocalDate viewDate) {
        this.viewDate = viewDate;
    }

    /**
     * Nustato peržiūrų skaičių.
     *
     * @param viewCount peržiūrų skaičius
     */
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}