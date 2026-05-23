CREATE TABLE movie_genres (
    movie_genres_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,
    CONSTRAINT fk_movie_genres_movie
        FOREIGN KEY (movie_id)
        REFERENCES movie(movie_id),
    CONSTRAINT fk_movie_genres_genre
        FOREIGN KEY (genre_id)
        REFERENCES genre(genre_id)
);