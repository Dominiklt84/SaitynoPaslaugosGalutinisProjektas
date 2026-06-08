package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * Entity, aprašantis filmą ir jo pagrindines savybes.
 */
@Entity
@Table(name = "movie")
@AttributeOverride(
        name = "id",
        column = @Column(name = "movie_id")
)
public class Movie extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "rated_id")
    private Rated rated;

    @Column(nullable = false)
    private String title;

    @Column(name = "movie_year")
    private String year;

    private String released;

    private String runtime;

    @Column(length = 2000)
    private String plot;

    private String awards;

    private String poster;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieView> movieViews;


    @ManyToMany
    @JoinTable(
            name = "movie_actors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors;

    @ManyToMany
    @JoinTable(
            name = "movie_directors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private List<Director> directors;

    @ManyToMany
    @JoinTable(
            name = "movie_writers",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "writer_id")
    )
    private List<Writer> writers;

    @ManyToMany
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(
            name = "movie_languages",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private List<Language> languages;

    @ManyToMany
    @JoinTable(
            name = "movie_countries",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private List<Country> countries;

    public Movie() {
    }

    /**
     * Grąžina filmo amžiaus cenzą.
     *
     * @return filmo amžiaus cenzas
     */
    public Rated getRated() {
        return rated;
    }

    /**
     * Nustato filmo amžiaus cenzą.
     *
     * @param rated filmo amžiaus cenzas
     */
    public void setRated(Rated rated) {
        this.rated = rated;
    }

    /**
     * Grąžina filmo pavadinimą.
     *
     * @return filmo pavadinimas
     */
    public String getTitle() {
        return title;
    }

    /**
     * Grąžina filmo išleidimo metus.
     *
     * @return filmo išleidimo metai
     */
    public String getYear() {
        return year;
    }

    /**
     * Grąžina filmo išleidimo datą.
     *
     * @return filmo išleidimo data
     */
    public String getReleased() {
        return released;
    }

    /**
     * Grąžina filmo trukmę.
     *
     * @return filmo trukmė
     */
    public String getRuntime() {
        return runtime;
    }

    /**
     * Grąžina filmo siužetą.
     *
     * @return filmo siužetas
     */
    public String getPlot() {
        return plot;
    }

    /**
     * Grąžina filmo apdovanojimus.
     *
     * @return filmo apdovanojimai
     */
    public String getAwards() {
        return awards;
    }

    /**
     * Grąžina filmo plakato nuorodą.
     *
     * @return plakato nuoroda
     */
    public String getPoster() {
        return poster;
    }

    /**
     * Grąžina filmo įvertinimų sąrašą.
     *
     * @return filmo įvertinimų sąrašas
     */
    public List<Rating> getRatings() {
        return ratings;
    }

    /**
     * Grąžina filmo aktorių sąrašą.
     *
     * @return filmo aktorių sąrašas
     */
    public List<Actor> getActors() {
        return actors;
    }

    /**
     * Grąžina filmo režisierių sąrašą.
     *
     * @return filmo režisierių sąrašas
     */
    public List<Director> getDirectors() {
        return directors;
    }

    /**
     * Grąžina filmo scenarijaus autorių sąrašą.
     *
     * @return filmo scenarijaus autorių sąrašas
     */
    public List<Writer> getWriters() {
        return writers;
    }

    /**
     * Grąžina filmo žanrų sąrašą.
     *
     * @return filmo žanrų sąrašas
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * Grąžina filmo kalbų sąrašą.
     *
     * @return filmo kalbų sąrašas
     */
    public List<Language> getLanguages() {
        return languages;
    }

    /**
     * Grąžina filmo kilmės šalių sąrašą.
     *
     * @return filmo kilmės šalių sąrašas
     */
    public List<Country> getCountries() {
        return countries;
    }

    /**
     * Nustato filmo pavadinimą.
     *
     * @param title filmo pavadinimas
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Nustato filmo išleidimo metus.
     *
     * @param year filmo išleidimo metai
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Nustato filmo išleidimo datą.
     *
     * @param released filmo išleidimo data
     */
    public void setReleased(String released) {
        this.released = released;
    }

    /**
     * Nustato filmo trukmę.
     *
     * @param runtime filmo trukmė
     */
    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    /**
     * Nustato filmo siužetą.
     *
     * @param plot filmo siužetas
     */
    public void setPlot(String plot) {
        this.plot = plot;
    }

    /**
     * Nustato filmo apdovanojimus.
     *
     * @param awards filmo apdovanojimai
     */
    public void setAwards(String awards) {
        this.awards = awards;
    }

    /**
     * Nustato filmo plakato nuorodą.
     *
     * @param poster plakato nuoroda
     */
    public void setPoster(String poster) {
        this.poster = poster;
    }

    /**
     * Nustato filmo įvertinimų sąrašą.
     *
     * @param ratings filmo įvertinimų sąrašas
     */
    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    /**
     * Nustato filmo aktorių sąrašą.
     *
     * @param actors filmo aktorių sąrašas
     */
    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    /**
     * Nustato filmo režisierių sąrašą.
     *
     * @param directors filmo režisierių sąrašas
     */
    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    /**
     * Nustato filmo scenarijaus autorių sąrašą.
     *
     * @param writers filmo scenarijaus autorių sąrašas
     */
    public void setWriters(List<Writer> writers) {
        this.writers = writers;
    }

    /**
     * Nustato filmo žanrų sąrašą.
     *
     * @param genres filmo žanrų sąrašas
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /**
     * Nustato filmo kalbų sąrašą.
     *
     * @param languages filmo kalbų sąrašas
     */
    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    /**
     * Nustato filmo kilmės šalių sąrašą.
     *
     * @param countries filmo kilmės šalių sąrašas
     */
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}