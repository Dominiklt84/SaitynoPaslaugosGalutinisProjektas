package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.service;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Movie;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.MovieView;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository.MovieViewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Servisas, skirtas filmų peržiūrų statistikos valdymui.
 * Registruoja ir analizuoja filmų peržiūrų informaciją.
 */
@Service
public class MovieViewService {

    private final MovieViewRepository movieViewRepository;

    /**
     * Sukuria naują peržiūrų statistikos servisą.
     *
     * @param movieViewRepository peržiūrų duomenų saugykla
     */
    public MovieViewService(MovieViewRepository movieViewRepository) {
        this.movieViewRepository = movieViewRepository;
    }

    /**
     * Užregistruoja filmo peržiūrą.
     * Jei peržiūra tą dieną jau egzistuoja,
     * padidinamas jos skaitiklis.
     *
     * @param movie peržiūrėtas filmas
     */
    public void registerView(Movie movie) {

        LocalDate today = LocalDate.now();

        MovieView view =
                movieViewRepository
                        .findByMovieAndViewDate(movie, today)
                        .orElse(null);

        if (view == null) {

            view = new MovieView();

            view.setMovie(movie);
            view.setViewDate(today);
            view.setViewCount(1);

        } else {

            view.setViewCount(
                    view.getViewCount() + 1
            );
        }

        movieViewRepository.save(view);
    }

    /**
     * Grąžina populiariausius šiandien peržiūrėtus filmus.
     *
     * @return filmų sąrašas
     */
    public List<Movie> getTopTodayMovies() {

        return movieViewRepository
                .findTopToday()
                .stream()
                .map(MovieView::getMovie)
                .distinct()
                .toList();
    }

    /**
     * Grąžina populiariausius šio mėnesio filmus.
     *
     * @return filmų sąrašas
     */
    public List<Movie> getTopMonthMovies() {

        return movieViewRepository
                .findTopThisMonth()
                .stream()
                .map(MovieView::getMovie)
                .distinct()
                .toList();
    }
}