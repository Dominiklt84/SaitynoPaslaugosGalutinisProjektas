CREATE TABLE rated (
                       rated_id BIGINT NOT NULL AUTO_INCREMENT,
                       title VARCHAR(50) NOT NULL,
                       PRIMARY KEY (rated_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_lithuanian_ci;

CREATE TABLE country (
                         country_id BIGINT NOT NULL AUTO_INCREMENT,
                         name VARCHAR(100) NOT NULL,
                         PRIMARY KEY (country_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_lithuanian_ci;

CREATE TABLE language (
                          language_id BIGINT NOT NULL AUTO_INCREMENT,
                          name VARCHAR(100) NOT NULL,
                          PRIMARY KEY (language_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_lithuanian_ci;

CREATE TABLE genre (
                       genre_id BIGINT NOT NULL AUTO_INCREMENT,
                       title VARCHAR(100) NOT NULL,
                       PRIMARY KEY (genre_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_lithuanian_ci;

CREATE TABLE director (
                          director_id BIGINT NOT NULL AUTO_INCREMENT,
                          first_name VARCHAR(100) NOT NULL,
                          last_name VARCHAR(100) NOT NULL,
                          PRIMARY KEY (director_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_lithuanian_ci;

CREATE TABLE actor (
                       actor_id BIGINT NOT NULL AUTO_INCREMENT,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                       PRIMARY KEY (actor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_lithuanian_ci;

CREATE TABLE writer (
                        writer_id BIGINT NOT NULL AUTO_INCREMENT,
                        first_name VARCHAR(100) NOT NULL,
                        last_name VARCHAR(100) NOT NULL,
                        PRIMARY KEY (writer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_lithuanian_ci;

CREATE TABLE movie (
                       movie_id BIGINT NOT NULL AUTO_INCREMENT,
                       rated_id BIGINT NOT NULL,

                       title VARCHAR(255) NOT NULL,
                       movie_year VARCHAR(10) NOT NULL,
                       released VARCHAR(50) NOT NULL,
                       runtime VARCHAR(50) NOT NULL,

                       plot VARCHAR(2000) NOT NULL,
                       awards VARCHAR(1000),
                       poster VARCHAR(1000),

                       PRIMARY KEY (movie_id),

                       CONSTRAINT uk_movie_title_year
                           UNIQUE (title, movie_year),

                       KEY idx_movie_rated_id (rated_id),

                       CONSTRAINT fk_movie_rated
                           FOREIGN KEY (rated_id)
                               REFERENCES rated (rated_id)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_lithuanian_ci;

CREATE TABLE rating (
                        rating_id BIGINT NOT NULL AUTO_INCREMENT,
                        movie_id BIGINT NOT NULL,
                        source VARCHAR(255) NOT NULL,
                        rating_value VARCHAR(50) NOT NULL,
                        PRIMARY KEY (rating_id),
                        KEY idx_rating_movie_id (movie_id),
                        CONSTRAINT fk_rating_movie
                            FOREIGN KEY (movie_id)
                                REFERENCES movie (movie_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_lithuanian_ci;

CREATE TABLE movie_actors (
                              movie_actors_id BIGINT NOT NULL AUTO_INCREMENT,
                              movie_id BIGINT NOT NULL,
                              actor_id BIGINT NOT NULL,
                              PRIMARY KEY (movie_actors_id),
                              KEY idx_movie_actors_movie_id (movie_id),
                              KEY idx_movie_actors_actor_id (actor_id),
                              CONSTRAINT fk_movie_actors_movie
                                  FOREIGN KEY (movie_id)
                                      REFERENCES movie (movie_id),
                              CONSTRAINT fk_movie_actors_actor
                                  FOREIGN KEY (actor_id)
                                      REFERENCES actor (actor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_lithuanian_ci;

CREATE TABLE movie_directors (
                                 movie_directors_id BIGINT NOT NULL AUTO_INCREMENT,
                                 movie_id BIGINT NOT NULL,
                                 director_id BIGINT NOT NULL,
                                 PRIMARY KEY (movie_directors_id),
                                 KEY idx_movie_directors_movie_id (movie_id),
                                 KEY idx_movie_directors_director_id (director_id),
                                 CONSTRAINT fk_movie_directors_movie
                                     FOREIGN KEY (movie_id)
                                         REFERENCES movie (movie_id),
                                 CONSTRAINT fk_movie_directors_director
                                     FOREIGN KEY (director_id)
                                         REFERENCES director (director_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_lithuanian_ci;

CREATE TABLE movie_genres (
                              movie_genres_id BIGINT NOT NULL AUTO_INCREMENT,
                              movie_id BIGINT NOT NULL,
                              genre_id BIGINT NOT NULL,
                              PRIMARY KEY (movie_genres_id),
                              KEY idx_movie_genres_movie_id (movie_id),
                              KEY idx_movie_genres_genre_id (genre_id),
                              CONSTRAINT fk_movie_genres_movie
                                  FOREIGN KEY (movie_id)
                                      REFERENCES movie (movie_id),
                              CONSTRAINT fk_movie_genres_genre
                                  FOREIGN KEY (genre_id)
                                      REFERENCES genre (genre_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_lithuanian_ci;

CREATE TABLE movie_languages (
                                 movie_languages_id BIGINT NOT NULL AUTO_INCREMENT,
                                 movie_id BIGINT NOT NULL,
                                 language_id BIGINT NOT NULL,
                                 PRIMARY KEY (movie_languages_id),
                                 KEY idx_movie_languages_movie_id (movie_id),
                                 KEY idx_movie_languages_language_id (language_id),
                                 CONSTRAINT fk_movie_languages_movie
                                     FOREIGN KEY (movie_id)
                                         REFERENCES movie (movie_id),
                                 CONSTRAINT fk_movie_languages_language
                                     FOREIGN KEY (language_id)
                                         REFERENCES language (language_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_lithuanian_ci;

CREATE TABLE movie_countries (
                                 movie_countries_id BIGINT NOT NULL AUTO_INCREMENT,
                                 movie_id BIGINT NOT NULL,
                                 country_id BIGINT NOT NULL,
                                 PRIMARY KEY (movie_countries_id),
                                 KEY idx_movie_countries_movie_id (movie_id),
                                 KEY idx_movie_countries_country_id (country_id),
                                 CONSTRAINT fk_movie_countries_movie
                                     FOREIGN KEY (movie_id)
                                         REFERENCES movie (movie_id),
                                 CONSTRAINT fk_movie_countries_country
                                     FOREIGN KEY (country_id)
                                         REFERENCES country (country_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_lithuanian_ci;

CREATE TABLE movie_writers (
                               movie_writers_id BIGINT NOT NULL AUTO_INCREMENT,
                               movie_id BIGINT NOT NULL,
                               writer_id BIGINT NOT NULL,
                               PRIMARY KEY (movie_writers_id),
                               KEY idx_movie_writers_movie_id (movie_id),
                               KEY idx_movie_writers_writer_id (writer_id),
                               CONSTRAINT fk_movie_writers_movie
                                   FOREIGN KEY (movie_id)
                                       REFERENCES movie (movie_id),
                               CONSTRAINT fk_movie_writers_writer
                                   FOREIGN KEY (writer_id)
                                       REFERENCES writer (writer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_lithuanian_ci;

CREATE TABLE movie_view (
                            movie_view_id BIGINT NOT NULL AUTO_INCREMENT,
                            movie_id BIGINT NOT NULL,
                            view_date TIMESTAMP NOT NULL,
                            view_count INT NOT NULL,
                            PRIMARY KEY (movie_view_id),
                            KEY idx_movie_view_movie_id (movie_id),
                            CONSTRAINT fk_movie_view_movie
                                FOREIGN KEY (movie_id)
                                    REFERENCES movie (movie_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_lithuanian_ci;
