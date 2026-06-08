package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.service;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Movie;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.MovieView;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository.MovieViewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testų klasė, tikrinanti MovieViewService veikimą.
 */
@ExtendWith(MockitoExtension.class)
class MovieViewServiceTest {

    @Mock private MovieViewRepository movieViewRepository;
    @InjectMocks private MovieViewService movieViewService;

    @Test
    void registerViewCreatesNewViewForToday() {
        Movie movie = movie("Dune", 1L);
        when(movieViewRepository.findByMovieAndViewDate(eq(movie), eq(LocalDate.now()))).thenReturn(Optional.empty());

        movieViewService.registerView(movie);

        ArgumentCaptor<MovieView> captor = ArgumentCaptor.forClass(MovieView.class);
        verify(movieViewRepository).save(captor.capture());
        assertEquals(movie, captor.getValue().getMovie());
        assertEquals(LocalDate.now(), captor.getValue().getViewDate());
        assertEquals(1, captor.getValue().getViewCount());
    }

    @Test
    void registerViewIncrementsExistingView() {
        Movie movie = movie("Dune", 1L);
        MovieView existing = new MovieView(movie, LocalDate.now(), 4);
        when(movieViewRepository.findByMovieAndViewDate(eq(movie), eq(LocalDate.now()))).thenReturn(Optional.of(existing));

        movieViewService.registerView(movie);

        assertEquals(5, existing.getViewCount());
        verify(movieViewRepository).save(existing);
    }

    @Test
    void getTopTodayMoviesReturnsDistinctMovies() {
        Movie dune = movie("Dune", 1L);
        Movie matrix = movie("Matrix", 2L);
        when(movieViewRepository.findTopToday()).thenReturn(List.of(
                new MovieView(dune, LocalDate.now(), 10),
                new MovieView(dune, LocalDate.now(), 7),
                new MovieView(matrix, LocalDate.now(), 5)
        ));

        assertEquals(List.of(dune, matrix), movieViewService.getTopTodayMovies());
    }

    @Test
    void getTopMonthMoviesReturnsDistinctMovies() {
        Movie movie = movie("Heat", 3L);
        when(movieViewRepository.findTopThisMonth()).thenReturn(List.of(
                new MovieView(movie, LocalDate.now(), 2),
                new MovieView(movie, LocalDate.now(), 1)
        ));

        assertEquals(List.of(movie), movieViewService.getTopMonthMovies());
    }

    private Movie movie(String title, Long id) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setId(id);
        return movie;
    }
}
