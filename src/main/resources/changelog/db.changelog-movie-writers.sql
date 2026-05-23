CREATE TABLE movie_writers (
    movie_writers_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id BIGINT NOT NULL,
    writer_id BIGINT NOT NULL,
    CONSTRAINT fk_movie_writers_movie
        FOREIGN KEY (movie_id)
        REFERENCES movie(movie_id),
    CONSTRAINT fk_movie_writers_writer
        FOREIGN KEY (writer_id)
        REFERENCES writer(writer_id)
);