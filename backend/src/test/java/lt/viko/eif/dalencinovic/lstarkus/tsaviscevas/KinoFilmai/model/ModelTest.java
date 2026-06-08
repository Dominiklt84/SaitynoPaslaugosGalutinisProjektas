package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testų klasė, tikrinanti modelių objektų veikimą.
 */
class ModelTest {

    @Test
    void simpleEntityConstructorsAndAccessorsWork() {
        Actor actor = new Actor("Tom", "Hardy");
        actor.setId(1L);
        actor.setFirstName("Christian");
        actor.setLastName("Bale");
        assertEquals(1L, actor.getId());
        assertEquals("Christian", actor.getFirstName());
        assertEquals("Bale", actor.getLastName());

        Director director = new Director("Denis", "Villeneuve");
        director.setFirstName("Christopher");
        director.setLastName("Nolan");
        assertEquals("Christopher", director.getFirstName());
        assertEquals("Nolan", director.getLastName());

        Writer writer = new Writer("Frank", "Herbert");
        writer.setFirstName("Jon");
        writer.setLastName("Spaihts");
        assertEquals("Jon", writer.getFirstName());
        assertEquals("Spaihts", writer.getLastName());

        Genre genre = new Genre("Drama");
        genre.setTitle("Sci-Fi");
        assertEquals("Sci-Fi", genre.getTitle());

        Rated rated = new Rated("R");
        rated.setTitle("PG-13");
        assertEquals("PG-13", rated.getTitle());

        Language language = new Language("Lithuanian");
        language.setName("English");
        assertEquals("English", language.getName());

        Country country = new Country("Lithuania");
        country.setName("USA");
        assertEquals("USA", country.getName());
    }

    @Test
    void movieAccessorsWork() {
        Movie movie = new Movie();
        Rated rated = new Rated("PG");
        List<Rating> ratings = List.of(new Rating());
        List<Actor> actors = List.of(new Actor("A", "B"));
        List<Director> directors = List.of(new Director("C", "D"));
        List<Writer> writers = List.of(new Writer("E", "F"));
        List<Genre> genres = List.of(new Genre("Action"));
        List<Language> languages = List.of(new Language("English"));
        List<Country> countries = List.of(new Country("USA"));

        movie.setRated(rated);
        movie.setTitle("Title");
        movie.setYear("2024");
        movie.setReleased("01 Jan 2024");
        movie.setRuntime("100 min");
        movie.setPlot("Plot");
        movie.setAwards("Awards");
        movie.setPoster("Poster");
        movie.setRatings(ratings);
        movie.setActors(actors);
        movie.setDirectors(directors);
        movie.setWriters(writers);
        movie.setGenres(genres);
        movie.setLanguages(languages);
        movie.setCountries(countries);

        assertSame(rated, movie.getRated());
        assertEquals("Title", movie.getTitle());
        assertEquals("2024", movie.getYear());
        assertEquals("01 Jan 2024", movie.getReleased());
        assertEquals("100 min", movie.getRuntime());
        assertEquals("Plot", movie.getPlot());
        assertEquals("Awards", movie.getAwards());
        assertEquals("Poster", movie.getPoster());
        assertSame(ratings, movie.getRatings());
        assertSame(actors, movie.getActors());
        assertSame(directors, movie.getDirectors());
        assertSame(writers, movie.getWriters());
        assertSame(genres, movie.getGenres());
        assertSame(languages, movie.getLanguages());
        assertSame(countries, movie.getCountries());
    }

    @Test
    void ratingAndMovieViewAccessorsWork() {
        Movie movie = new Movie();
        Rating rating = new Rating(movie, "IMDb", "8/10");
        rating.setMovie(movie);
        rating.setSource("Rotten Tomatoes");
        rating.setValue("90%");
        assertSame(movie, rating.getMovie());
        assertEquals("Rotten Tomatoes", rating.getSource());
        assertEquals("90%", rating.getValue());

        LocalDate date = LocalDate.of(2026, 1, 1);
        MovieView movieView = new MovieView(movie, date, 4);
        movieView.setMovie(movie);
        movieView.setViewDate(date.plusDays(1));
        movieView.setViewCount(5);
        assertSame(movie, movieView.getMovie());
        assertEquals(date.plusDays(1), movieView.getViewDate());
        assertEquals(5, movieView.getViewCount());
    }
}
