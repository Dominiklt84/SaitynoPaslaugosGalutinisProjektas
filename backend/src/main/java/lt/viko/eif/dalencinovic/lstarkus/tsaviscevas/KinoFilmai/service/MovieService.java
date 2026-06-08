package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.service;

import jakarta.transaction.Transactional;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.factory.OmdbMovieFactory;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Movie;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository.MovieRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servisas, atsakingas už filmų logikos vykdymą.
 * Atlieka filmų duomenų apdorojimą ir ryšį su duomenų baze.
 */
@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final OmdbService omdbService;
    private final OmdbMovieFactory omdbMovieMapper;
    private final MovieViewService movieViewService;

    /**
     * Sukuria naują MovieService objektą.
     *
     * @param movieRepository filmų saugykla
     * @param omdbService OMDb API servisas
     * @param omdbMovieMapper filmų objektų kūrimo komponentas
     * @param movieViewService peržiūrų statistikos servisas
     */
    public MovieService(MovieRepository movieRepository,
                        OmdbService omdbService,
                        OmdbMovieFactory omdbMovieMapper, MovieViewService movieViewService) {
        this.movieRepository = movieRepository;
        this.omdbService = omdbService;
        this.omdbMovieMapper = omdbMovieMapper;
        this.movieViewService = movieViewService;
    }

    /**
     * Grąžina visus sistemoje esančius filmus.
     *
     * @return filmų sąrašas
     */
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    /**
     * Suranda filmą pagal identifikatorių.
     * Radus filmą registruojama jo peržiūra.
     *
     * @param id filmo identifikatorius
     * @return rastas filmas arba tuščias rezultatas
     */
    public Optional<Movie> getMovieById(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);

        movie.ifPresent(movieViewService::registerView);
        return movie;
    }

    /**
     * Išsaugo filmą duomenų bazėje.
     *
     * @param movie išsaugomas filmas
     * @return išsaugotas filmas
     */
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    /**
     * Pašalina filmą pagal identifikatorių.
     *
     * @param id šalinamo filmo identifikatorius
     */
    @Transactional
    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        movieRepository.delete(movie);
    }

    /**
     * Ieško filmų pagal pavadinimą.
     *
     * @param title filmo pavadinimas
     * @return rastų filmų sąrašas
     */
    public List<Movie> searchMovies(String title) {
        return movieRepository.findByTitleIgnoreCase(title);
    }

    /**
     * Suranda filmą duomenų bazėje arba gauna jį iš OMDb API.
     * Gautas filmas išsaugomas lokaliai, jei jo dar nėra.
     *
     * @param title filmo pavadinimas
     * @return rastas arba naujai sukurtas filmo objektas
     */
    @Cacheable(value = "movies", key = "#title.toLowerCase().trim()")
    public Movie getOrFetchMovie(String title) {

        List<Movie> existingByRequestTitle =
                movieRepository.findByTitleIgnoreCase(title);

        if (!existingByRequestTitle.isEmpty()) {
            return existingByRequestTitle.get(0);
        }

        Movie movie = omdbMovieMapper.toMovie(
                omdbService.searchMovieByTitle(title)
        );

        if (movie == null) {
            return null;
        }

        List<Movie> existingByRealTitle =
                movieRepository.findByTitleIgnoreCase(movie.getTitle());

        if (!existingByRealTitle.isEmpty()) {
            return existingByRealTitle.get(0);
        }

        return movieRepository.save(movie);
    }
}
