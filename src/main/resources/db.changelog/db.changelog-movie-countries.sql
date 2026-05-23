CREATE TABLE movie_countries (
    movie_countries_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id BIGINT NOT NULL,
    country_id BIGINT NOT NULL,
    CONSTRAINT fk_movie_countries_movie
    FOREIGN KEY (movie_id)
    REFERENCES movie(movie_id),
    CONSTRAINT fk_movie_countries_country
    FOREIGN KEY (country_id)
    REFERENCES country(country_id)
);