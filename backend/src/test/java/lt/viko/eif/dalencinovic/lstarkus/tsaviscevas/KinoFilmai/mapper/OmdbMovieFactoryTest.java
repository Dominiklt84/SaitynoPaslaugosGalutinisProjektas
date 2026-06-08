package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.mapper;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.dto.OmdbMovieResponse;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.factory.OmdbMovieFactory;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.*;
import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OmdbMovieFactoryTest {

    @Test
    void toMovieMapsResponseUsingExistingRepositoryObjects() {
        RatedRepository ratedRepository = mock(RatedRepository.class);
        GenreRepository genreRepository = mock(GenreRepository.class);
        ActorRepository actorRepository = mock(ActorRepository.class);
        DirectorRepository directorRepository = mock(DirectorRepository.class);
        WriterRepository writerRepository = mock(WriterRepository.class);
        LanguageRepository languageRepository = mock(LanguageRepository.class);
        CountryRepository countryRepository = mock(CountryRepository.class);
        OmdbMovieFactory mapper = new OmdbMovieFactory(ratedRepository, genreRepository, actorRepository,
                directorRepository, writerRepository, languageRepository, countryRepository);

        when(ratedRepository.findByTitle("PG")).thenReturn(Optional.of(new Rated("PG")));
        when(genreRepository.findByTitle("Drama")).thenReturn(Optional.of(new Genre("Drama")));
        when(actorRepository.findByFirstNameAndLastName("Tom", "Hanks")).thenReturn(Optional.of(new Actor("Tom", "Hanks")));
        when(directorRepository.findByFirstNameAndLastName("Robert", "Zemeckis")).thenReturn(Optional.of(new Director("Robert", "Zemeckis")));
        when(writerRepository.findByFirstNameAndLastName("Eric", "Roth")).thenReturn(Optional.of(new Writer("Eric", "Roth")));
        when(languageRepository.findByName("English")).thenReturn(Optional.of(new Language("English")));
        when(countryRepository.findByName("USA")).thenReturn(Optional.of(new Country("USA")));

        OmdbMovieResponse response = new OmdbMovieResponse();
        response.setTitle("Forrest Gump");
        response.setRated("PG");
        response.setGenre("Drama");
        response.setActors("Tom Hanks");
        response.setDirector("Robert Zemeckis");
        response.setWriter("Eric Roth");
        response.setLanguage("English");
        response.setCountry("USA");

        Movie movie = mapper.toMovie(response);

        assertEquals("Forrest Gump", movie.getTitle());
        assertEquals("PG", movie.getRated().getTitle());
        assertEquals("Drama", movie.getGenres().get(0).getTitle());
        assertEquals("Tom", movie.getActors().get(0).getFirstName());
        assertEquals("Zemeckis", movie.getDirectors().get(0).getLastName());
        assertEquals("Roth", movie.getWriters().get(0).getLastName());
        assertEquals("English", movie.getLanguages().get(0).getName());
        assertEquals("USA", movie.getCountries().get(0).getName());
        verify(ratedRepository, never()).save(any());
    }
}
