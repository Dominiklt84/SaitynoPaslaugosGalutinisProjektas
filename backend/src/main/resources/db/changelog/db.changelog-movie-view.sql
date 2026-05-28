CREATE TABLE movie_view (
    movie_view_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id BIGINT NOT NULL,
    view_date TIMESTAMP NOT NULL,
    view_count INT NOT NULL,
    CONSTRAINT fk_movie_view_movie
    FOREIGN KEY (movie_id)
    REFERENCES movie(movie_id)
);