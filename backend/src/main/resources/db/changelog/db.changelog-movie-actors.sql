CREATE TABLE movie_actors (
    movie_actors_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id BIGINT NOT NULL,
    actor_id BIGINT NOT NULL,
    CONSTRAINT fk_movie_actors_movie
        FOREIGN KEY (movie_id)
        REFERENCES movie(movie_id),
    CONSTRAINT fk_movie_actors_actor
        FOREIGN KEY (actor_id)
        REFERENCES actor(actor_id)
);