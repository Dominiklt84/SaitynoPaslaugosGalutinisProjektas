import { useEffect, useState } from "react";

import MovieCard from "../components/MovieCard";

import { getTopTodayMovies } from "../services/movieService";

import "../styles/TopMovies.css";

function TopTodayPage() {

    const [movies, setMovies] = useState([]);

    useEffect(() => {

        async function loadMovies() {

            const data = await getTopTodayMovies();

            setMovies(data._embedded.movieList);
        }

        loadMovies();

    }, []);

    return (

        <div className="top-page">

            <h1 className="page-title">
                Top Movies Today
            </h1>

            <div className="movies-grid">

                {movies.map(movie => (

                    <MovieCard
                        key={movie.id}
                        movie={movie}
                    />

                ))}

            </div>

        </div>
    );
}

export default TopTodayPage;