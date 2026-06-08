package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents movie view statistics.
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

    public Movie getMovie() {
        return movie;
    }

    public LocalDate getViewDate() {
        return viewDate;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setViewDate(LocalDate viewDate) {
        this.viewDate = viewDate;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}