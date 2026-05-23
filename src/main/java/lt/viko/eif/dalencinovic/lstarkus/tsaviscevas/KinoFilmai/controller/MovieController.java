package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.controller;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Movie;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id,
                                             @RequestBody Movie updatedMovie) {

        return movieService.getMovieById(id)
                .map(movie -> {

                    movie.setTitle(updatedMovie.getTitle());
                    movie.setYear(updatedMovie.getYear());
                    movie.setPlot(updatedMovie.getPlot());

                    return ResponseEntity.ok(movieService.saveMovie(movie));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {

        if (movieService.getMovieById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        movieService.deleteMovie(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Movie> searchMovies(@RequestParam String title) {
        return movieService.searchMovies(title);
    }

    @GetMapping("/omdb")
    public ResponseEntity<Movie> getMovieFromOmdb(
            @RequestParam String title) {

        Movie movie = movieService.getOrFetchMovie(title);

        if (movie == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(movie);
    }
}