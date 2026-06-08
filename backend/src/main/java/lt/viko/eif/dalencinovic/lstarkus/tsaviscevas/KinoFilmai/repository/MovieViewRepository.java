package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Movie;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.MovieView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Saugykla, skirta filmų peržiūrų duomenų valdymui.
 */
@Repository
public interface MovieViewRepository extends JpaRepository<MovieView, Long> {

    Optional<MovieView> findByMovieAndViewDate(Movie movie, LocalDate viewDate);

    @Query("""
    SELECT mv
    FROM MovieView mv
    WHERE mv.viewDate = CURRENT_DATE
    ORDER BY mv.viewCount DESC
    """)
    List<MovieView> findTopToday();


    @Query("""
    SELECT mv
    FROM MovieView mv
    WHERE YEAR(mv.viewDate) = YEAR(CURRENT_DATE)
    AND MONTH(mv.viewDate) = MONTH(CURRENT_DATE)
    ORDER BY mv.viewCount DESC
    """)
    List<MovieView> findTopThisMonth();
}