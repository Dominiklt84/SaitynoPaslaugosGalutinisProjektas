package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Testų klasė, tikrinanti OmdbMovieResponse objektą.
 */
class OmdbMovieResponseTest {

    @Test
    void accessorsWork() {
        OmdbMovieResponse response = new OmdbMovieResponse();
        OmdbMovieResponse.OmdbRatingResponse rating = new OmdbMovieResponse.OmdbRatingResponse();
        rating.setSource("IMDb");
        rating.setValue("8.5/10");
        List<OmdbMovieResponse.OmdbRatingResponse> ratings = List.of(rating);

        response.setTitle("Title");
        response.setYear("2026");
        response.setReleased("01 Jan 2026");
        response.setRuntime("120 min");
        response.setPlot("Plot");
        response.setAwards("Awards");
        response.setPoster("Poster");
        response.setResponse("True");
        response.setRated("PG");
        response.setGenre("Drama");
        response.setActors("Actor Name");
        response.setDirector("Director Name");
        response.setWriter("Writer Name");
        response.setLanguage("English");
        response.setCountry("USA");
        response.setRatings(ratings);

        assertEquals("Title", response.getTitle());
        assertEquals("2026", response.getYear());
        assertEquals("01 Jan 2026", response.getReleased());
        assertEquals("120 min", response.getRuntime());
        assertEquals("Plot", response.getPlot());
        assertEquals("Awards", response.getAwards());
        assertEquals("Poster", response.getPoster());
        assertEquals("True", response.getResponse());
        assertEquals("PG", response.getRated());
        assertEquals("Drama", response.getGenre());
        assertEquals("Actor Name", response.getActors());
        assertEquals("Director Name", response.getDirector());
        assertEquals("Writer Name", response.getWriter());
        assertEquals("English", response.getLanguage());
        assertEquals("USA", response.getCountry());
        assertSame(ratings, response.getRatings());
        assertEquals("IMDb", response.getRatings().get(0).getSource());
        assertEquals("8.5/10", response.getRatings().get(0).getValue());
    }
}
