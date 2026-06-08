package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.factory;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.dto.OmdbMovieResponse;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.*;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Maps OMDb API response data to project entities.
 */
@Component
public class OmdbMovieFactory {

    private final RatedRepository ratedRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final WriterRepository writerRepository;
    private final LanguageRepository languageRepository;
    private final CountryRepository countryRepository;

    public OmdbMovieFactory(RatedRepository ratedRepository,
                            GenreRepository genreRepository,
                            ActorRepository actorRepository,
                            DirectorRepository directorRepository,
                            WriterRepository writerRepository,
                            LanguageRepository languageRepository,
                            CountryRepository countryRepository) {
        this.ratedRepository = ratedRepository;
        this.genreRepository = genreRepository;
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
        this.writerRepository = writerRepository;
        this.languageRepository = languageRepository;
        this.countryRepository = countryRepository;
    }

    public Movie toMovie(OmdbMovieResponse response) {

        if (response == null ||
                !"True".equalsIgnoreCase(response.getResponse())) {

            return null;
        }

        Movie movie = new Movie();

        movie.setTitle(response.getTitle());
        movie.setYear(response.getYear());
        movie.setReleased(response.getReleased());
        movie.setRuntime(response.getRuntime());
        movie.setPlot(response.getPlot());
        movie.setAwards(response.getAwards());
        movie.setPoster(response.getPoster());

        movie.setRated(resolveRated(response.getRated()));
        movie.setGenres(resolveGenres(response.getGenre()));
        movie.setActors(resolveActors(response.getActors()));
        movie.setDirectors(resolveDirectors(response.getDirector()));
        movie.setWriters(resolveWriters(response.getWriter()));
        movie.setLanguages(resolveLanguages(response.getLanguage()));
        movie.setCountries(resolveCountries(response.getCountry()));
        movie.setRatings(resolveRatings(response.getRatings(), movie));

        return movie;
    }

    private Rated resolveRated(String ratedTitle) {

        if (isBlankOrNotAvailable(ratedTitle)) {

            return ratedRepository.findByTitle("Unknown")
                    .orElseGet(() ->
                            ratedRepository.save(
                                    new Rated("Unknown")
                            )
                    );
        }

        return ratedRepository.findByTitle(ratedTitle)
                .orElseGet(() ->
                        ratedRepository.save(
                                new Rated(ratedTitle)
                        )
                );
    }

    private List<Genre> resolveGenres(String genreText) {
        List<Genre> genres = new ArrayList<>();

        for (String genreName : splitValues(genreText)) {
            Genre genre = genreRepository.findByTitle(genreName)
                    .orElseGet(() -> genreRepository.save(new Genre(genreName)));
            genres.add(genre);
        }

        return genres;
    }

    private List<Actor> resolveActors(String actorText) {
        List<Actor> actors = new ArrayList<>();

        for (String actorName : splitValues(actorText)) {
            String[] nameParts = splitName(actorName);

            Actor actor = actorRepository.findByFirstNameAndLastName(nameParts[0], nameParts[1])
                    .orElseGet(() -> actorRepository.save(new Actor(nameParts[0], nameParts[1])));
            actors.add(actor);
        }

        return actors;
    }

    private List<Director> resolveDirectors(String directorText) {
        List<Director> directors = new ArrayList<>();

        for (String directorName : splitValues(directorText)) {
            String[] nameParts = splitName(directorName);

            Director director = directorRepository.findByFirstNameAndLastName(nameParts[0], nameParts[1])
                    .orElseGet(() -> directorRepository.save(new Director(nameParts[0], nameParts[1])));
            directors.add(director);
        }

        return directors;
    }

    private List<Writer> resolveWriters(String writerText) {
        List<Writer> writers = new ArrayList<>();

        for (String writerName : splitValues(writerText)) {
            String[] nameParts = splitName(writerName);

            Writer writer = writerRepository.findByFirstNameAndLastName(nameParts[0], nameParts[1])
                    .orElseGet(() -> writerRepository.save(new Writer(nameParts[0], nameParts[1])));
            writers.add(writer);
        }

        return writers;
    }

    private List<Language> resolveLanguages(String languageText) {
        List<Language> languages = new ArrayList<>();

        for (String languageName : splitValues(languageText)) {
            Language language = languageRepository.findByName(languageName)
                    .orElseGet(() -> languageRepository.save(new Language(languageName)));
            languages.add(language);
        }

        return languages;
    }

    private List<Country> resolveCountries(String countryText) {
        List<Country> countries = new ArrayList<>();

        for (String countryName : splitValues(countryText)) {
            Country country = countryRepository.findByName(countryName)
                    .orElseGet(() -> countryRepository.save(new Country(countryName)));
            countries.add(country);
        }

        return countries;
    }

    private List<Rating> resolveRatings(List<OmdbMovieResponse.OmdbRatingResponse> ratingResponses,
                                        Movie movie) {
        List<Rating> ratings = new ArrayList<>();

        if (ratingResponses == null) {
            return ratings;
        }

        for (OmdbMovieResponse.OmdbRatingResponse ratingResponse : ratingResponses) {
            Rating rating = new Rating();
            rating.setSource(ratingResponse.getSource());
            rating.setValue(ratingResponse.getValue());
            rating.setMovie(movie);
            ratings.add(rating);
        }

        return ratings;
    }

    private List<String> splitValues(String value) {
        List<String> values = new ArrayList<>();

        if (isBlankOrNotAvailable(value)) {
            return values;
        }

        String[] parts = value.split(",");

        for (String part : parts) {
            String trimmedPart = part.trim();

            if (!trimmedPart.isEmpty()) {
                values.add(trimmedPart);
            }
        }

        return values;
    }

    private String[] splitName(String fullName) {
        String[] nameParts = fullName.trim().split(" ", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        return new String[]{firstName, lastName};
    }

    private boolean isBlankOrNotAvailable(String value) {
        return value == null || value.isBlank() || value.equalsIgnoreCase("N/A");
    }
}
