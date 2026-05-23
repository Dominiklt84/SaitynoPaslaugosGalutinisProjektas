CREATE TABLE movie (
    movie_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rated_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    year VARCHAR(10) NOT NULL,
    released VARCHAR(50) NOT NULL,
    runtime VARCHAR(50) NOT NULL,
    plot VARCHAR(2000) NOT NULL,
    awards VARCHAR(1000),
    poster VARCHAR(1000),
    CONSTRAINT fk_movie_rated
    FOREIGN KEY (rated_id)
    REFERENCES rated(rated_id)
);