import { useState, useEffect } from "react";

import SearchBar from "../components/SearchBar";
import MovieCard from "../components/MovieCard";

import {
    searchMovie,
    getAllMovies
} from "../services/movieService";


import "../styles/HomePage.css";

function HomePage() {

    const [movie, setMovie] = useState(null);

    const [notFound, setNotFound] = useState(false);

    const [allMovies, setAllMovies] = useState([]);

    useEffect(() => {

        async function loadMovies() {

            const data = await getAllMovies();

            setAllMovies(data);
        }

        loadMovies();

    }, []);

    const handleSearch = async (title) => {

    try {

        const result = await searchMovie(title);

        if (!result) {
            setMovie(null);
            setNotFound(true);
            return;
        }

        setMovie(result);
        setNotFound(false);

    } catch (error) {

        setMovie(null);
        setNotFound(true);

        console.error(error);
    }
};

    return (
        <div className="home-page">

            <SearchBar onSearch={handleSearch} />

            {notFound && (
                <h2 className="not-found">
                    Movie not found
                </h2>
            )}
            <div className="movie-container">
                {movie && (
                    <MovieCard movie={movie} />
                )}
            </div>

            <h2 className="section-title">
                All Movies
            </h2>

            <div className="movies-grid">

                {allMovies.map(movie => (

                    <MovieCard
                        key={movie.id}
                        movie={movie}
                    />

                ))}

            </div>

        </div>
    );
}

export default HomePage;