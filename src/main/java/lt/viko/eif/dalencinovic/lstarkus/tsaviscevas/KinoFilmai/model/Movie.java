package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * Represents movie entity.
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

    public Rated getRated() {
        return rated;
    }

    public void setRated(Rated rated) {
        this.rated = rated;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getPlot() {
        return plot;
    }

    public String getAwards() {
        return awards;
    }

    public String getPoster() {
        return poster;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public List<Writer> getWriters() {
        return writers;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public void setWriters(List<Writer> writers) {
        this.writers = writers;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}