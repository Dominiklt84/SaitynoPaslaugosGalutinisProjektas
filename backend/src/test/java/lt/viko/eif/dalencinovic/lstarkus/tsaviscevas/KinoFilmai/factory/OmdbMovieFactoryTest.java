package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.factory;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.dto.OmdbMovieResponse;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.*;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OmdbMovieFactoryTest {

    @Mock private RatedRepository ratedRepository;
    @Mock private GenreRepository genreRepository;
    @Mock private ActorRepository actorRepository;
    @Mock private DirectorRepository directorRepository;
    @Mock private WriterRepository writerRepository;
    @Mock private LanguageRepository languageRepository;
    @Mock private CountryRepository countryRepository;

    private OmdbMovieFactory factory;

    @BeforeEach
    void setUp() {
        factory = new OmdbMovieFactory(ratedRepository, genreRepository, actorRepository,
                directorRepository, writerRepository, languageRepository, countryRepository);
    }

    @Test
    void toMovieMapsFullOmdbResponseAndCreatesMissingReferenceData() {
        OmdbMovieResponse response = validResponse();
        OmdbMovieResponse.OmdbRatingResponse ratingResponse = new OmdbMovieResponse.OmdbRatingResponse();
        ratingResponse.setSource("Internet Movie Database");
        ratingResponse.setValue("8.8/10");
        response.setRatings(List.of(ratingResponse));

        when(ratedRepository.findByTitle("PG-13")).thenReturn(Optional.empty());
        when(ratedRepository.save(any(Rated.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(genreRepository.findByTitle(anyString())).thenReturn(Optional.empty());
        when(genreRepository.save(any(Genre.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(actorRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.empty());
        when(actorRepository.save(any(Actor.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(directorRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.empty());
        when(directorRepository.save(any(Director.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(writerRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.empty());
        when(writerRepository.save(any(Writer.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(languageRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(languageRepository.save(any(Language.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(countryRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(countryRepository.save(any(Country.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Movie movie = factory.toMovie(response);

        assertEquals("Inception", movie.getTitle());
        assertEquals("2010", movie.getYear());
        assertEquals("16 Jul 2010", movie.getReleased());
        assertEquals("148 min", movie.getRuntime());
        assertEquals("A thief enters dreams.", movie.getPlot());
        assertEquals("Won 4 Oscars", movie.getAwards());
        assertEquals("poster.jpg", movie.getPoster());
        assertEquals("PG-13", movie.getRated().getTitle());
        assertEquals(List.of("Action", "Sci-Fi"), movie.getGenres().stream().map(Genre::getTitle).toList());
        assertEquals("Leonardo", movie.getActors().get(0).getFirstName());
        assertEquals("DiCaprio", movie.getActors().get(0).getLastName());
        assertEquals("Christopher", movie.getDirectors().get(0).getFirstName());
        assertEquals("Nolan", movie.getDirectors().get(0).getLastName());
        assertEquals("English", movie.getLanguages().get(0).getName());
        assertEquals("USA", movie.getCountries().get(0).getName());
        assertEquals(1, movie.getRatings().size());
        assertSame(movie, movie.getRatings().get(0).getMovie());
    }

    @Test
    void toMovieReusesExistingReferenceData() {
        OmdbMovieResponse response = validResponse();
        Rated rated = new Rated("PG-13");
        Genre genre = new Genre("Action");
        Actor actor = new Actor("Leonardo", "DiCaprio");
        Director director = new Director("Christopher", "Nolan");
        Writer writer = new Writer("Jonathan", "Nolan");
        Language language = new Language("English");
        Country country = new Country("USA");

        when(ratedRepository.findByTitle("PG-13")).thenReturn(Optional.of(rated));
        when(genreRepository.findByTitle("Action")).thenReturn(Optional.of(genre));
        when(genreRepository.findByTitle("Sci-Fi")).thenReturn(Optional.of(new Genre("Sci-Fi")));
        when(actorRepository.findByFirstNameAndLastName("Leonardo", "DiCaprio")).thenReturn(Optional.of(actor));
        when(actorRepository.findByFirstNameAndLastName("Joseph", "Gordon-Levitt")).thenReturn(Optional.of(new Actor("Joseph", "Gordon-Levitt")));
        when(directorRepository.findByFirstNameAndLastName("Christopher", "Nolan")).thenReturn(Optional.of(director));
        when(writerRepository.findByFirstNameAndLastName("Jonathan", "Nolan")).thenReturn(Optional.of(writer));
        when(languageRepository.findByName("English")).thenReturn(Optional.of(language));
        when(countryRepository.findByName("USA")).thenReturn(Optional.of(country));

        Movie movie = factory.toMovie(response);

        assertSame(rated, movie.getRated());
        assertSame(genre, movie.getGenres().get(0));
        assertSame(actor, movie.getActors().get(0));
        assertSame(director, movie.getDirectors().get(0));
        assertSame(writer, movie.getWriters().get(0));
        assertSame(language, movie.getLanguages().get(0));
        assertSame(country, movie.getCountries().get(0));
        verify(ratedRepository, never()).save(any());
    }

    @Test
    void toMovieIgnoresBlankAndNotAvailableValues() {
        OmdbMovieResponse response = new OmdbMovieResponse();
        response.setTitle("Minimal");
        response.setRated("N/A");
        response.setGenre(" ");
        response.setActors(null);
        response.setDirector("N/A");
        response.setWriter("");
        response.setLanguage("N/A");
        response.setCountry("   ");
        response.setRatings(null);

        Movie movie = factory.toMovie(response);

        assertEquals("Minimal", movie.getTitle());
        assertNull(movie.getRated());
        assertTrue(movie.getGenres().isEmpty());
        assertTrue(movie.getActors().isEmpty());
        assertTrue(movie.getDirectors().isEmpty());
        assertTrue(movie.getWriters().isEmpty());
        assertTrue(movie.getLanguages().isEmpty());
        assertTrue(movie.getCountries().isEmpty());
        assertTrue(movie.getRatings().isEmpty());
        verifyNoInteractions(ratedRepository, genreRepository, actorRepository, directorRepository,
                writerRepository, languageRepository, countryRepository);
    }

    @Test
    void toMovieSupportsSingleWordNames() {
        OmdbMovieResponse response = validResponse();
        response.setActors("Madonna");
        response.setDirector("Prince");
        response.setWriter("Sting");
        when(ratedRepository.findByTitle(anyString())).thenReturn(Optional.of(new Rated("PG-13")));
        when(genreRepository.findByTitle(anyString())).thenAnswer(invocation -> Optional.of(new Genre(invocation.getArgument(0))));
        when(actorRepository.findByFirstNameAndLastName("Madonna", "")).thenReturn(Optional.of(new Actor("Madonna", "")));
        when(directorRepository.findByFirstNameAndLastName("Prince", "")).thenReturn(Optional.of(new Director("Prince", "")));
        when(writerRepository.findByFirstNameAndLastName("Sting", "")).thenReturn(Optional.of(new Writer("Sting", "")));
        when(languageRepository.findByName(anyString())).thenAnswer(invocation -> Optional.of(new Language(invocation.getArgument(0))));
        when(countryRepository.findByName(anyString())).thenAnswer(invocation -> Optional.of(new Country(invocation.getArgument(0))));

        Movie movie = factory.toMovie(response);

        assertEquals("Madonna", movie.getActors().get(0).getFirstName());
        assertEquals("", movie.getActors().get(0).getLastName());
        assertEquals("Prince", movie.getDirectors().get(0).getFirstName());
        assertEquals("", movie.getWriters().get(0).getLastName());
    }

    private OmdbMovieResponse validResponse() {
        OmdbMovieResponse response = new OmdbMovieResponse();
        response.setTitle("Inception");
        response.setYear("2010");
        response.setReleased("16 Jul 2010");
        response.setRuntime("148 min");
        response.setPlot("A thief enters dreams.");
        response.setAwards("Won 4 Oscars");
        response.setPoster("poster.jpg");
        response.setRated("PG-13");
        response.setGenre("Action, Sci-Fi");
        response.setActors("Leonardo DiCaprio, Joseph Gordon-Levitt");
        response.setDirector("Christopher Nolan");
        response.setWriter("Jonathan Nolan");
        response.setLanguage("English");
        response.setCountry("USA");
        return response;
    }
}
