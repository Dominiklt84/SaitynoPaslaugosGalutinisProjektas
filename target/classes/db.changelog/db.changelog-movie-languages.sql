CREATE TABLE movie_languages (
    movie_languages_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id BIGINT NOT NULL,
    language_id BIGINT NOT NULL,
    CONSTRAINT fk_movie_languages_movie
        FOREIGN KEY (movie_id)
        REFERENCES movie(movie_id),
    CONSTRAINT fk_movie_languages_language
        FOREIGN KEY (language_id)
    REFERENCES language(language_id)
);