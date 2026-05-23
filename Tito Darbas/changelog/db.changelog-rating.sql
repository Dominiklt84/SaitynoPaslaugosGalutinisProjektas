CREATE TABLE rating (
    rating_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id BIGINT NOT NULL,
    source VARCHAR(255) NOT NULL,
    value VARCHAR(50) NOT NULL,
    CONSTRAINT fk_rating_movie
    FOREIGN KEY (movie_id)
    REFERENCES movie(movie_id)
);