package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.service;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.dto.OmdbMovieResponse;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.mapper.OmdbMovieMapper;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Movie;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final OmdbService omdbService;
    private final OmdbMovieMapper omdbMovieMapper;

    public MovieService(MovieRepository movieRepository,
                        OmdbService omdbService,
                        OmdbMovieMapper omdbMovieMapper) {
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
        return movieRepository.findByTitle(title);
    }

    public Movie getOrFetchMovie(String title) {
        List<Movie> movies = movieRepository.findByTitle(title);

        if (!movies.isEmpty()) {
            return movies.get(0);
        }

        OmdbMovieResponse response = omdbService.searchMovieByTitle(title);

        if (response == null || "False".equalsIgnoreCase(response.getResponse())) {
            return null;
        }

        Movie movie = omdbMovieMapper.toMovie(response);

        return movieRepository.save(movie);
    }
}
