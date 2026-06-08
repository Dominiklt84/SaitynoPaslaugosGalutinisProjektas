package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.service;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Movie;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.MovieView;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository.MovieViewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovieViewService {

    private final MovieViewRepository movieViewRepository;

    public MovieViewService(MovieViewRepository movieViewRepository) {
        this.movieViewRepository = movieViewRepository;
    }

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

    public List<Movie> getTopTodayMovies() {

        return movieViewRepository
                .findTopToday()
                .stream()
                .map(MovieView::getMovie)
                .distinct()
                .toList();
    }


    public List<Movie> getTopMonthMovies() {

        return movieViewRepository
                .findTopThisMonth()
                .stream()
                .map(MovieView::getMovie)
                .distinct()
                .toList();
    }
}