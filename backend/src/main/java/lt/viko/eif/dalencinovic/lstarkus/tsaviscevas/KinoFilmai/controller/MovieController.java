package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.controller;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Movie;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public CollectionModel<EntityModel<Movie>> getAllMovies() {

        List<EntityModel<Movie>> movies =
                movieService.getAllMovies()
                        .stream()
                        .map(movie -> EntityModel.of(movie,
                                linkTo(methodOn(MovieController.class)
                                        .getMovieById(movie.getId()))
                                        .withSelfRel()))
                        .toList();

        return CollectionModel.of(
                movies,
                linkTo(methodOn(MovieController.class)
                        .getAllMovies())
                        .withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Movie>> getMovieById(@PathVariable Long id) {

        return movieService.getMovieById(id)
                .map(movie -> EntityModel.of(movie,
                        linkTo(methodOn(MovieController.class)
                                .getMovieById(id))
                                .withSelfRel(),

                        linkTo(methodOn(MovieController.class)
                                .getAllMovies())
                                .withRel("all-movies")
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Movie>> createMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieService.saveMovie(movie);

        EntityModel<Movie> model = EntityModel.of(savedMovie,
                linkTo(methodOn(MovieController.class).getMovieById(savedMovie.getId())).withSelfRel(),
                linkTo(methodOn(MovieController.class).getAllMovies()).withRel("all-movies")
        );

        return ResponseEntity
                .created(linkTo(methodOn(MovieController.class).getMovieById(savedMovie.getId())).toUri())
                .body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Movie>> updateMovie(@PathVariable Long id,
                                                          @RequestBody Movie updatedMovie) {

        return movieService.getMovieById(id)
                .map(movie -> {
                    movie.setTitle(updatedMovie.getTitle());
                    movie.setYear(updatedMovie.getYear());
                    movie.setPlot(updatedMovie.getPlot());

                    Movie savedMovie = movieService.saveMovie(movie);

                    EntityModel<Movie> model = EntityModel.of(savedMovie,
                            linkTo(methodOn(MovieController.class).getMovieById(id)).withSelfRel(),
                            linkTo(methodOn(MovieController.class).getAllMovies()).withRel("all-movies")
                    );

                    return ResponseEntity.ok(model);
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
    public CollectionModel<EntityModel<Movie>> searchMovies(@RequestParam String title) {
        List<EntityModel<Movie>> movies = movieService.searchMovies(title)
                .stream()
                .map(movie -> EntityModel.of(movie,
                        linkTo(methodOn(MovieController.class).getMovieById(movie.getId())).withSelfRel()
                ))
                .toList();

        return CollectionModel.of(
                movies,
                linkTo(methodOn(MovieController.class).searchMovies(title)).withSelfRel(),
                linkTo(methodOn(MovieController.class).getAllMovies()).withRel("all-movies")
        );
    }

    @GetMapping("/omdb")
    public ResponseEntity<EntityModel<Movie>> getMovieFromOmdb(
            @RequestParam String title) {

        Movie movie = movieService.getOrFetchMovie(title);

        if (movie == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Movie> model = EntityModel.of(
                movie,
                linkTo(methodOn(MovieController.class)
                        .getMovieById(movie.getId()))
                        .withSelfRel(),

                linkTo(methodOn(MovieController.class)
                        .getAllMovies())
                        .withRel("all-movies")
        );

        return ResponseEntity.ok(model);
    }
}