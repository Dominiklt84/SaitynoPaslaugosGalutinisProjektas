package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.service;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.factory.OmdbMovieFactory;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Movie;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository.MovieRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final OmdbService omdbService;
    private final OmdbMovieFactory omdbMovieMapper;

    public MovieService(MovieRepository movieRepository,
                        OmdbService omdbService,
                        OmdbMovieFactory omdbMovieMapper) {
        this.movieRepository = movieRepository;
        this.omdbService = omdbService;
        this.omdbMovieMapper = omdbMovieMapper;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public List<Movie> searchMovies(String title) {
        return movieRepository.findByTitleIgnoreCase(title);
    }

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
