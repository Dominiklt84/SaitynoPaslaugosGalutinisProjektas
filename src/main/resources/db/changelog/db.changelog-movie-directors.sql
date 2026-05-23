CREATE TABLE movie_directors (
    movie_directors_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id BIGINT NOT NULL,
    director_id BIGINT NOT NULL,
    CONSTRAINT fk_movie_directors_movie
    FOREIGN KEY (movie_id)
    REFERENCES movie(movie_id),
    CONSTRAINT fk_movie_directors_director
    FOREIGN KEY (director_id)
    REFERENCES director(director_id)
);