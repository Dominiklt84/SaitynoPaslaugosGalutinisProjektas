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

    /**
     * Grąžina filmo įvertinimų sąrašą.
     *
     * @return filmo įvertinimų sąrašas
     */
    public List<OmdbRatingResponse> getRatings() {
        return Ratings;
    }

    /**
     * Nustato filmo įvertinimų sąrašą.
     *
     * @param ratings filmo įvertinimų sąrašas
     */
    public void setRatings(List<OmdbRatingResponse> ratings) {
        Ratings = ratings;
    }

    /**
     * DTO objektas, aprašantis filmo įvertinimą,
     * grąžinamą iš OMDb API.
     */
    public static class OmdbRatingResponse {

        private String Source;
        private String Value;

        /**
         * Grąžina įvertinimo šaltinį.
         *
         * @return įvertinimo šaltinis
         */
        public String getSource() {
            return Source;
        }

        /**
         * Nustato įvertinimo šaltinį.
         *
         * @param source įvertinimo šaltinis
         */
        public void setSource(String source) {
            Source = source;
        }

        /**
         * Grąžina įvertinimo reikšmę.
         *
         * @return įvertinimo reikšmė
         */
        public String getValue() {
            return Value;
        }

        /**
         * Nustato įvertinimo reikšmę.
         *
         * @param value įvertinimo reikšmė
         */
        public void setValue(String value) {
            Value = value;
        }
    }

    /**
     * Grąžina filmo šalį.
     *
     * @return filmo šalis
     */
    public String getCountry() {
        return Country;
    }

    /**
     * Nustato filmo šalį.
     *
     * @param country filmo šalis
     */
    public void setCountry(String country) {
        Country = country;
    }

    /**
     * Grąžina filmo kalbą.
     *
     * @return filmo kalba
     */
    public String getLanguage() {
        return Language;
    }

    /**
     * Nustato filmo kalbą.
     *
     * @param language filmo kalba
     */
    public void setLanguage(String language) {
        Language = language;
    }

    /**
     * Grąžina filmo scenarijaus autorių.
     *
     * @return scenarijaus autorius
     */
    public String getWriter() {
        return Writer;
    }

    /**
     * Nustato filmo scenarijaus autorių.
     *
     * @param writer scenarijaus autorius
     */
    public void setWriter(String writer) {
        Writer = writer;
    }

    /**
     * Grąžina filmo režisierių.
     *
     * @return filmo režisierius
     */
    public String getDirector() {
        return Director;
    }

    /**
     * Nustato filmo režisierių.
     *
     * @param director filmo režisierius
     */
    public void setDirector(String director) {
        Director = director;
    }

    /**
     * Grąžina filmo aktorius.
     *
     * @return filmo aktoriai
     */
    public String getActors() {
        return Actors;
    }

    /**
     * Nustato filmo aktorius.
     *
     * @param actors filmo aktoriai
     */
    public void setActors(String actors) {
        Actors = actors;
    }

    /**
     * Grąžina filmo žanrą.
     *
     * @return filmo žanras
     */
    public String getGenre() {
        return Genre;
    }

    /**
     * Nustato filmo žanrą.
     *
     * @param genre filmo žanras
     */
    public void setGenre(String genre) {
        Genre = genre;
    }

    /**
     * Grąžina filmo amžiaus cenzą.
     *
     * @return filmo amžiaus cenzas
     */
    public String getRated() {
        return Rated;
    }

    /**
     * Nustato filmo amžiaus cenzą.
     *
     * @param rated filmo amžiaus cenzas
     */
    public void setRated(String rated) {
        Rated = rated;
    }

    /**
     * Grąžina filmo pavadinimą.
     *
     * @return filmo pavadinimas
     */
    public String getTitle() {
        return Title;
    }

    /**
     * Nustato filmo pavadinimą.
     *
     * @param title filmo pavadinimas
     */
    public void setTitle(String title) {
        Title = title;
    }

    /**
     * Grąžina filmo išleidimo metus.
     *
     * @return filmo išleidimo metai
     */
    public String getYear() {
        return Year;
    }

    /**
     * Nustato filmo išleidimo metus.
     *
     * @param year filmo išleidimo metai
     */
    public void setYear(String year) {
        Year = year;
    }

    /**
     * Grąžina filmo išleidimo datą.
     *
     * @return filmo išleidimo data
     */
    public String getReleased() {
        return Released;
    }

    /**
     * Nustato filmo išleidimo datą.
     *
     * @param released filmo išleidimo data
     */
    public void setReleased(String released) {
        Released = released;
    }

    /**
     * Grąžina filmo trukmę.
     *
     * @return filmo trukmė
     */
    public String getRuntime() {
        return Runtime;
    }

    /**
     * Nustato filmo trukmę.
     *
     * @param runtime filmo trukmė
     */
    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    /**
     * Grąžina filmo siužetą.
     *
     * @return filmo siužetas
     */
    public String getPlot() {
        return Plot;
    }

    /**
     * Nustato filmo siužetą.
     *
     * @param plot filmo siužetas
     */
    public void setPlot(String plot) {
        Plot = plot;
    }

    /**
     * Grąžina filmo apdovanojimus.
     *
     * @return filmo apdovanojimai
     */
    public String getAwards() {
        return Awards;
    }

    /**
     * Nustato filmo apdovanojimus.
     *
     * @param awards filmo apdovanojimai
     */
    public void setAwards(String awards) {
        Awards = awards;
    }

    /**
     * Grąžina filmo plakato nuorodą.
     *
     * @return plakato nuoroda
     */
    public String getPoster() {
        return Poster;
    }

    /**
     * Nustato filmo plakato nuorodą.
     *
     * @param poster plakato nuoroda
     */
    public void setPoster(String poster) {
        Poster = poster;
    }

    /**
     * Grąžina OMDb API atsakymo būseną.
     *
     * @return atsakymo būsena
     */
    public String getResponse() {
        return Response;
    }

    /**
     * Nustato OMDb API atsakymo būseną.
     *
     * @param response atsakymo būsena
     */
    public void setResponse(String response) {
        Response = response;
    }
}