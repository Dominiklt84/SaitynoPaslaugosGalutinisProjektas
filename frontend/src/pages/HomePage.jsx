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

    const [allMovies, setAllMovies] = useState([]);

    useEffect(() => {

        async function loadMovies() {

            const data =
                await getAllMovies();

            setAllMovies(data);
        }

        loadMovies();

    }, []);

    const handleSearch = async (title) => {

    try {

        const result = await searchMovie(title);

        console.log("MOVIE:", result);
        console.log("TITLE:", result.title);
        console.log("POSTER:", result.poster);

        setMovie(result);

    } catch (error) {

        console.error("ERROR:", error);
    }
};

    return (
        <div className="home-page">

            <SearchBar onSearch={handleSearch} />

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