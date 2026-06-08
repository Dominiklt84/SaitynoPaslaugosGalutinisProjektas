import { useState, useEffect } from "react";

import SearchBar from "../components/SearchBar";
import MovieCard from "../components/MovieCard";

import {
    searchMovie,
} from "../services/movieService";

import "../styles/HomePage.css";
function HomePage() {

    const [movie, setMovie] = useState(null);

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

            <h1 className="home-title">
                Movie Explorer
            </h1>

            <SearchBar onSearch={handleSearch} />


            <div className="movie-container">
                {movie && (
                    <MovieCard movie={movie} />
                )}
            </div>

        </div>
    );
}

export default HomePage;