package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.dto;

import java.util.List;

/**
 * DTO objektas, aprašantis OMDb API grąžinamą atsakymą.
 * Naudojamas duomenų perdavimui tarp sistemų.
 */
public class OmdbMovieResponse {

    private String Title;
    private String Year;
    private String Released;
    private String Runtime;
    private String Plot;
    private String Awards;
    private String Poster;
    private String Response;

    private String Rated;
    private String Genre;
    private String Actors;
    private String Director;
    private String Writer;
    private String Language;
    private String Country;

    private List<OmdbRatingResponse> Ratings;

    public List<OmdbRatingResponse> getRatings() {
        return Ratings;
    }

    public void setRatings(List<OmdbRatingResponse> ratings) {
        Ratings = ratings;
    }

    public static class OmdbRatingResponse {

        private String Source;
        private String Value;

        public String getSource() {
            return Source;
        }

        public void setSource(String source) {
            Source = source;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String value) {
            Value = value;
        }
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String writer) {
        Writer = writer;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getRated() {
        return Rated;
    }

    public void setRated(String rated) {
        Rated = rated;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String released) {
        Released = released;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public String getAwards() {
        return Awards;
    }

    public void setAwards(String awards) {
        Awards = awards;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}