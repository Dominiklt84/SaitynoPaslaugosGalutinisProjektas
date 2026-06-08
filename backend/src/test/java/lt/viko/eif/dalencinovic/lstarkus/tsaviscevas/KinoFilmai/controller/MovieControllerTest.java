package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.controller;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Movie;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.service.MovieService;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.service.MovieViewService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testų klasė, tikrinanti MovieController funkcionalumą.
 */
class MovieControllerTest {

    private MovieService movieService;
    private MovieViewService movieViewService;
    private MovieController controller;

    @BeforeEach
    void setUp() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
        movieService = mock(MovieService.class);
        movieViewService = mock(MovieViewService.class);
        controller = new MovieController(movieService, movieViewService);
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void getAllMoviesReturnsCollectionWithSelfLinks() {
        Movie movie = movie("Inception", 1L);
        when(movieService.getAllMovies()).thenReturn(List.of(movie));

        CollectionModel<EntityModel<Movie>> result = controller.getAllMovies();

        assertEquals(1, result.getContent().size());
        assertTrue(result.getLink("self").isPresent());
        assertEquals(movie, result.getContent().iterator().next().getContent());
    }

    @Test
    void getMovieByIdReturnsOkWithLinksWhenFound() {
        Movie movie = movie("Inception", 1L);
        when(movieService.getMovieById(1L)).thenReturn(Optional.of(movie));

        ResponseEntity<EntityModel<Movie>> response = controller.getMovieById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(movie, response.getBody().getContent());
        assertTrue(response.getBody().getLink("self").isPresent());
        assertTrue(response.getBody().getLink("all-movies").isPresent());
    }

    @Test
    void getMovieByIdReturnsNotFoundWhenMissing() {
        when(movieService.getMovieById(404L)).thenReturn(Optional.empty());

        assertEquals(HttpStatus.NOT_FOUND, controller.getMovieById(404L).getStatusCode());
    }

    @Test
    void createMovieReturnsCreatedModel() {
        Movie unsaved = movie("Dune", null);
        Movie saved = movie("Dune", 2L);
        when(movieService.saveMovie(unsaved)).thenReturn(saved);

        ResponseEntity<EntityModel<Movie>> response = controller.createMovie(unsaved);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(saved, response.getBody().getContent());
        assertNotNull(response.getHeaders().getLocation());
    }

    @Test
    void updateMovieUpdatesOnlyProvidedFields() {
        Movie existing = movie("Old", 3L);
        existing.setYear("1999");
        existing.setPlot("Old plot");
        Movie update = new Movie();
        update.setTitle("New");
        update.setPlot("New plot");
        when(movieService.getMovieById(3L)).thenReturn(Optional.of(existing));
        when(movieService.saveMovie(existing)).thenReturn(existing);

        ResponseEntity<EntityModel<Movie>> response = controller.updateMovie(3L, update);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("New", existing.getTitle());
        assertEquals("1999", existing.getYear());
        assertEquals("New plot", existing.getPlot());
        verify(movieService).saveMovie(existing);
    }

    @Test
    void updateMovieReturnsNotFoundWhenMissing() {
        when(movieService.getMovieById(5L)).thenReturn(Optional.empty());

        assertEquals(HttpStatus.NOT_FOUND, controller.updateMovie(5L, new Movie()).getStatusCode());
    }

    @Test
    void deleteMovieDeletesWhenExists() {
        Movie movie = movie("Heat", 4L);
        when(movieService.getMovieById(4L)).thenReturn(Optional.of(movie));

        ResponseEntity<Void> response = controller.deleteMovie(4L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(movieService).deleteMovie(4L);
    }

    @Test
    void deleteMovieReturnsNotFoundWhenMissing() {
        when(movieService.getMovieById(4L)).thenReturn(Optional.empty());

        assertEquals(HttpStatus.NOT_FOUND, controller.deleteMovie(4L).getStatusCode());
        verify(movieService, never()).deleteMovie(anyLong());
    }

    @Test
    void searchMoviesReturnsMatchingMovies() {
        Movie movie = movie("Arrival", 6L);
        when(movieService.searchMovies("arrival")).thenReturn(List.of(movie));

        CollectionModel<EntityModel<Movie>> result = controller.searchMovies("arrival");

        assertEquals(1, result.getContent().size());
        assertTrue(result.getLink("self").isPresent());
        assertTrue(result.getLink("all-movies").isPresent());
    }

    @Test
    void getMovieFromOmdbReturnsOkWhenFound() {
        Movie movie = movie("Arrival", 6L);
        when(movieService.getOrFetchMovie("arrival")).thenReturn(movie);

        assertEquals(HttpStatus.OK, controller.getMovieFromOmdb("arrival").getStatusCode());
    }

    @Test
    void getMovieFromOmdbReturnsNotFoundWhenNull() {
        when(movieService.getOrFetchMovie("missing")).thenReturn(null);

        assertEquals(HttpStatus.NOT_FOUND, controller.getMovieFromOmdb("missing").getStatusCode());
    }

    @Test
    void topEndpointsDelegateToMovieViewService() {
        Movie today = movie("Today", 1L);
        Movie month = movie("Month", 2L);
        when(movieViewService.getTopTodayMovies()).thenReturn(List.of(today));
        when(movieViewService.getTopMonthMovies()).thenReturn(List.of(month));

        assertEquals(List.of(today), controller.getTopTodayMovies());
        assertEquals(List.of(month), controller.getTopMonthMovies());
    }

    private Movie movie(String title, Long id) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        return movie;
    }
}
