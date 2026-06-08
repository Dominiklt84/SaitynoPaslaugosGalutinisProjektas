package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.service;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.dto.OmdbMovieResponse;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.factory.OmdbMovieFactory;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Movie;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock private MovieRepository movieRepository;
    @Mock private OmdbService omdbService;
    @Mock private OmdbMovieFactory omdbMovieFactory;
    @Mock private MovieViewService movieViewService;

    @InjectMocks private MovieService movieService;

    @Test
    void getAllMoviesReturnsRepositoryMovies() {
        Movie movie = movie("Inception", 1L);
        when(movieRepository.findAll()).thenReturn(List.of(movie));

        assertEquals(List.of(movie), movieService.getAllMovies());
        verify(movieRepository).findAll();
    }

    @Test
    void getMovieByIdRegistersViewWhenMovieExists() {
        Movie movie = movie("Inception", 1L);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Optional<Movie> result = movieService.getMovieById(1L);

        assertTrue(result.isPresent());
        assertEquals(movie, result.get());
        verify(movieViewService).registerView(movie);
    }

    @Test
    void getMovieByIdDoesNotRegisterViewWhenMovieMissing() {
        when(movieRepository.findById(99L)).thenReturn(Optional.empty());

        assertTrue(movieService.getMovieById(99L).isEmpty());
        verifyNoInteractions(movieViewService);
    }

    @Test
    void saveMovieDelegatesToRepository() {
        Movie movie = movie("Matrix", null);
        Movie saved = movie("Matrix", 5L);
        when(movieRepository.save(movie)).thenReturn(saved);

        assertEquals(saved, movieService.saveMovie(movie));
    }

    @Test
    void deleteMovieDeletesById() {
        movieService.deleteMovie(7L);
        verify(movieRepository).deleteById(7L);
    }

    @Test
    void searchMoviesDelegatesToRepository() {
        Movie movie = movie("Interstellar", 2L);
        when(movieRepository.findByTitleIgnoreCase("interstellar")).thenReturn(List.of(movie));

        assertEquals(List.of(movie), movieService.searchMovies("interstellar"));
    }

    @Test
    void getOrFetchMovieReturnsExistingMovieByRequestedTitle() {
        Movie existing = movie("The Matrix", 1L);
        when(movieRepository.findByTitleIgnoreCase("matrix")).thenReturn(List.of(existing));

        Movie result = movieService.getOrFetchMovie("matrix");

        assertEquals(existing, result);
        verifyNoInteractions(omdbService, omdbMovieFactory);
        verify(movieRepository, never()).save(any());
    }

    @Test
    void getOrFetchMovieReturnsNullWhenOmdbMappingReturnsNull() {
        OmdbMovieResponse response = new OmdbMovieResponse();
        when(movieRepository.findByTitleIgnoreCase("unknown")).thenReturn(List.of());
        when(omdbService.searchMovieByTitle("unknown")).thenReturn(response);
        when(omdbMovieFactory.toMovie(response)).thenReturn(null);

        assertNull(movieService.getOrFetchMovie("unknown"));
        verify(movieRepository, never()).save(any());
    }

    @Test
    void getOrFetchMovieReturnsExistingMovieByRealTitle() {
        OmdbMovieResponse response = new OmdbMovieResponse();
        Movie mapped = movie("The Matrix", null);
        Movie existing = movie("The Matrix", 3L);
        when(movieRepository.findByTitleIgnoreCase("matrix")).thenReturn(List.of());
        when(omdbService.searchMovieByTitle("matrix")).thenReturn(response);
        when(omdbMovieFactory.toMovie(response)).thenReturn(mapped);
        when(movieRepository.findByTitleIgnoreCase("The Matrix")).thenReturn(List.of(existing));

        assertEquals(existing, movieService.getOrFetchMovie("matrix"));
        verify(movieRepository, never()).save(mapped);
    }

    @Test
    void getOrFetchMovieSavesNewMovieWhenNotExisting() {
        OmdbMovieResponse response = new OmdbMovieResponse();
        Movie mapped = movie("Arrival", null);
        Movie saved = movie("Arrival", 10L);
        when(movieRepository.findByTitleIgnoreCase("arrival")).thenReturn(List.of());
        when(omdbService.searchMovieByTitle("arrival")).thenReturn(response);
        when(omdbMovieFactory.toMovie(response)).thenReturn(mapped);
        when(movieRepository.findByTitleIgnoreCase("Arrival")).thenReturn(List.of());
        when(movieRepository.save(mapped)).thenReturn(saved);

        assertEquals(saved, movieService.getOrFetchMovie("arrival"));
    }

    private Movie movie(String title, Long id) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        return movie;
    }
}
