package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.factory;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.dto.OmdbMovieResponse;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.*;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Konvertuoja OMDb API atsakymus į objektus.
 * Užtikrina duomenų transformavimą.
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

    /**
     * Sukuria OMDb filmų objektų factory.
     * Inicijuoja visas reikalingas saugyklas duomenų paieškai ir išsaugojimui.
     *
     * @param ratedRepository    amžiaus reitingų saugykla
     * @param genreRepository    žanrų saugykla
     * @param actorRepository    aktorių saugykla
     * @param directorRepository režisierių saugykla
     * @param writerRepository   scenaristų saugykla
     * @param languageRepository kalbų saugykla
     * @param countryRepository  šalių saugykla
     */
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

    /****
     * Konvertuoja OMDb API atsakymą į Movie objektą.
     *
     * @param response OMDb API atsakymo objektas
     * @return sukurtas Movie objektas arba null, jei atsakymas negaliojantis
     */
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

    /**
     * Suranda arba sukuria filmo amžiaus reitingą.
     * Jei reitingas nenurodytas arba neprieinamas,
     * naudojama reikšmė „Unknown“.
     *
     * @param ratedTitle amžiaus reitingo pavadinimas
     * @return rastas arba naujai sukurtas Rated objektas
     */
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

    /**
     * Konvertuoja žanrų tekstinį sąrašą į Genre objektų sąrašą.
     * Kiekvienas žanras surandamas duomenų bazėje arba sukuriamas,
     * jei toks įrašas dar neegzistuoja.
     *
     * @param genreText žanrų sąrašas tekstiniu formatu
     * @return žanrų objektų sąrašas
     */
    private List<Genre> resolveGenres(String genreText) {
        List<Genre> genres = new ArrayList<>();

        for (String genreName : splitValues(genreText)) {
            Genre genre = genreRepository.findByTitle(genreName)
                    .orElseGet(() -> genreRepository.save(new Genre(genreName)));
            genres.add(genre);
        }

        return genres;
    }

    /**
     * Konvertuoja aktorių tekstinį sąrašą į Actor objektų sąrašą.
     * Kiekvienas aktorius surandamas duomenų bazėje pagal vardą ir pavardę
     * arba sukuriamas, jei toks įrašas dar neegzistuoja.
     *
     * @param actorText aktorių sąrašas tekstiniu formatu
     * @return aktorių objektų sąrašas
     */
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

    /**
     * Konvertuoja režisierių tekstinį sąrašą į Director objektų sąrašą.
     * Kiekvienas režisierius surandamas duomenų bazėje pagal vardą ir pavardę
     * arba sukuriamas, jei toks įrašas dar neegzistuoja.
     *
     * @param directorText režisierių sąrašas tekstiniu formatu
     * @return režisierių objektų sąrašas
     */
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

    /**
     * Konvertuoja scenaristų tekstinį sąrašą į Writer objektų sąrašą.
     * Kiekvienas scenaristas surandamas duomenų bazėje pagal vardą ir pavardę
     * arba sukuriamas, jei toks įrašas dar neegzistuoja.
     *
     * @param writerText scenaristų sąrašas tekstiniu formatu
     * @return scenaristų objektų sąrašas
     */
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

    /**
     * Konvertuoja kalbų tekstinį sąrašą į Language objektų sąrašą.
     * Kiekviena kalba surandama duomenų bazėje arba sukuriama,
     * jei toks įrašas dar neegzistuoja.
     *
     * @param languageText kalbų sąrašas tekstiniu formatu
     * @return kalbų objektų sąrašas
     */
    private List<Language> resolveLanguages(String languageText) {
        List<Language> languages = new ArrayList<>();

        for (String languageName : splitValues(languageText)) {
            Language language = languageRepository.findByName(languageName)
                    .orElseGet(() -> languageRepository.save(new Language(languageName)));
            languages.add(language);
        }

        return languages;
    }

    /**
     * Konvertuoja šalių tekstinį sąrašą į Country objektų sąrašą.
     * Kiekviena šalis surandama duomenų bazėje arba sukuriama,
     * jei toks įrašas dar neegzistuoja.
     *
     * @param countryText šalių sąrašas tekstiniu formatu
     * @return šalių objektų sąrašas
     */
    private List<Country> resolveCountries(String countryText) {
        List<Country> countries = new ArrayList<>();

        for (String countryName : splitValues(countryText)) {
            Country country = countryRepository.findByName(countryName)
                    .orElseGet(() -> countryRepository.save(new Country(countryName)));
            countries.add(country);
        }

        return countries;
    }

    /**
     * Konvertuoja OMDb API vertinimų sąrašą į Rating objektų sąrašą.
     * Kiekvienas vertinimas susiejamas su nurodytu filmu.
     *
     * @param ratingResponses OMDb API grąžintas vertinimų sąrašas
     * @param movie           filmas, kuriam priskiriami vertinimai
     * @return vertinimų objektų sąrašas
     */
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

    /**
     * Suskaido tekstinę reikšmę pagal kablelius ir pašalina
     * nereikalingus tarpus.
     *
     * @param value tekstinė reikšmė, kurioje elementai atskirti kableliais
     * @return suskaidytų reikšmių sąrašas; jei reikšmė tuščia arba
     * neprieinama, grąžinamas tuščias sąrašas
     */
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

    /**
     * Suskaido pilną asmens vardą į vardą ir pavardę.
     * Jei pavardė nenurodyta, grąžinama tuščia reikšmė.
     *
     * @param fullName pilnas asmens vardas
     * @return masyvas, kuriame pirmas elementas yra vardas,
     * o antras – pavardė
     */
    private String[] splitName(String fullName) {
        String[] nameParts = fullName.trim().split(" ", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        return new String[]{firstName, lastName};
    }

    /**
     * Patikrina, ar pateikta reikšmė yra tuščia,
     * null arba žymima kaip neprieinama („N/A“).
     *
     * @param value tikrinama tekstinė reikšmė
     * @return true, jei reikšmė yra null, tuščia arba „N/A“,
     *         kitu atveju false
     */
    private boolean isBlankOrNotAvailable(String value) {
        return value == null || value.isBlank() || value.equalsIgnoreCase("N/A");
    }
}
