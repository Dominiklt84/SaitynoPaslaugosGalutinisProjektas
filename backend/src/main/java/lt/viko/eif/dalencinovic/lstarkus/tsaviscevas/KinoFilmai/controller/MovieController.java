package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.controller;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Movie;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.service.MovieService;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.service.MovieViewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * REST valdiklis, skirtas filmų duomenų valdymui.
 * Leidžia gauti, kurti, atnaujinti ir šalinti filmus.
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieViewService movieViewService;

    /**
     * Sukuria naują valdiklio objektą.
     *
     * @param movieService servisas, atsakingas už filmų valdymą
     * @param movieViewService servisas, atsakingas už filmų peržiūrų statistiką
     */
    public MovieController(MovieService movieService, MovieViewService movieViewService) {
        this.movieService = movieService;
        this.movieViewService = movieViewService;
    }

    /**
     * Grąžina visų sistemoje esančių filmų sąrašą.
     *
     * @return filmų kolekcija su HATEOAS nuorodomis
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Movie>>> getAllMovies() {

        List<Movie> movieList = movieService.getAllMovies();

        if (movieList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<EntityModel<Movie>> movies =
                movieList.stream()
                        .map(movie -> EntityModel.of(movie,
                                linkTo(methodOn(MovieController.class)
                                        .getMovieById(movie.getId()))
                                        .withSelfRel()))
                        .toList();

        CollectionModel<EntityModel<Movie>> collectionModel =
                CollectionModel.of(
                        movies,
                        linkTo(methodOn(MovieController.class)
                                .getAllMovies())
                                .withSelfRel()
                );

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Suranda filmą pagal identifikatorių.
     *
     * @param id filmo identifikatorius
     * @return rastas filmas arba 404 klaida, jei filmas nerastas
     */
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

    /**
     * Sukuria naują filmą ir išsaugo jį duomenų bazėje.
     *
     * @param movie kuriamas filmas
     * @return sukurtas filmas su HATEOAS nuorodomis
     */
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

    /**
     * Atnaujina esamo filmo informaciją.
     *
     * @param id atnaujinamo filmo identifikatorius
     * @param updatedMovie nauji filmo duomenys
     * @return atnaujintas filmas arba 404 klaida, jei filmas nerastas
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Movie>> updateMovie(@PathVariable Long id,
                                                          @RequestBody Movie updatedMovie) {

        return movieService.getMovieById(id)
                .map(movie -> {
                    if (updatedMovie.getTitle() != null) {
                        movie.setTitle(updatedMovie.getTitle());
                    }

                    if (updatedMovie.getYear() != null) {
                        movie.setYear(updatedMovie.getYear());
                    }

                    if (updatedMovie.getPlot() != null) {
                        movie.setPlot(updatedMovie.getPlot());
                    }

                    Movie savedMovie = movieService.saveMovie(movie);

                    EntityModel<Movie> model = EntityModel.of(savedMovie,
                            linkTo(methodOn(MovieController.class).getMovieById(id)).withSelfRel(),
                            linkTo(methodOn(MovieController.class).getAllMovies()).withRel("all-movies")
                    );

                    return ResponseEntity.ok(model);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Pašalina filmą iš duomenų bazės.
     *
     * @param id šalinamo filmo identifikatorius
     * @return 204 atsakymas sėkmingo pašalinimo atveju
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {

        if (movieService.getMovieById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        movieService.deleteMovie(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Ieško filmų pagal pavadinimą.
     *
     * @param title ieškomo filmo pavadinimas
     * @return rastų filmų kolekcija
     */
    @GetMapping("/search")
    public ResponseEntity<CollectionModel<EntityModel<Movie>>> searchMovies(@RequestParam String title) {

        List<Movie> movieList = movieService.searchMovies(title);

        if (movieList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<EntityModel<Movie>> movies = movieList.stream()
                .map(movie -> EntityModel.of(movie,
                        linkTo(methodOn(MovieController.class)
                                .getMovieById(movie.getId()))
                                .withSelfRel()
                ))
                .toList();

        CollectionModel<EntityModel<Movie>> collectionModel =
                CollectionModel.of(
                        movies,
                        linkTo(methodOn(MovieController.class)
                                .searchMovies(title))
                                .withSelfRel(),
                        linkTo(methodOn(MovieController.class)
                                .getAllMovies())
                                .withRel("all-movies")
                );

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Ieško filmo OMDb sistemoje.
     * Jei filmas jau egzistuoja duomenų bazėje,
     * grąžinamas esamas įrašas.
     *
     * @param title filmo pavadinimas
     * @return rastas filmas arba 404 klaida
     */
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

    /**
     * Grąžina populiariausius šiandien peržiūrėtus filmus.
     *
     * @return filmų sąrašas pagal dienos peržiūrų statistiką
     */
    @GetMapping("/top/day")
    public ResponseEntity<CollectionModel<EntityModel<Movie>>> getTopTodayMovies() {

        List<EntityModel<Movie>> movies =
                movieViewService.getTopTodayMovies()
                        .stream()
                        .map(movie -> EntityModel.of(
                                movie,
                                linkTo(methodOn(MovieController.class)
                                        .getMovieById(movie.getId()))
                                        .withSelfRel()
                        ))
                        .toList();

        if (movies.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CollectionModel<EntityModel<Movie>> model =
                CollectionModel.of(
                        movies,
                        linkTo(methodOn(MovieController.class)
                                .getTopTodayMovies())
                                .withSelfRel(),

                        linkTo(methodOn(MovieController.class)
                                .getTopMonthMovies())
                                .withRel("top-month"),

                        linkTo(methodOn(MovieController.class)
                                .getAllMovies())
                                .withRel("all-movies")
                );

        return ResponseEntity.ok(model);
    }

    /**
     * Grąžina populiariausius šio mėnesio filmus.
     *
     * @return filmų sąrašas pagal mėnesio peržiūrų statistiką
     */
    @GetMapping("/top/month")
    public ResponseEntity<CollectionModel<EntityModel<Movie>>> getTopMonthMovies() {

        List<EntityModel<Movie>> movies =
                movieViewService.getTopMonthMovies()
                        .stream()
                        .map(movie -> EntityModel.of(
                                movie,
                                linkTo(methodOn(MovieController.class)
                                        .getMovieById(movie.getId()))
                                        .withSelfRel()
                        ))
                        .toList();

        if (movies.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CollectionModel<EntityModel<Movie>> model =
                CollectionModel.of(
                        movies,
                        linkTo(methodOn(MovieController.class)
                                .getTopMonthMovies())
                                .withSelfRel(),

                        linkTo(methodOn(MovieController.class)
                                .getTopTodayMovies())
                                .withRel("top-day"),

                        linkTo(methodOn(MovieController.class)
                                .getAllMovies())
                                .withRel("all-movies")
                );

        return ResponseEntity.ok(model);
    }
}