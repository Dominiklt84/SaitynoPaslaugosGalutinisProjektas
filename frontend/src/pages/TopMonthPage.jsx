import { useEffect, useState } from "react";

import MovieCard from "../components/MovieCard";

import { getTopMonthMovies } from "../services/movieService";

import "../styles/TopMovies.css";

function TopMonthPage() {

    const [movies, setMovies] = useState([]);

    useEffect(() => {

        async function loadMovies() {

            const data = await getTopMonthMovies();

            setMovies(data._embedded.movieList);
        }

        loadMovies();

    }, []);

    return (

         <div className="top-page">

            <h1 className="page-title">
                Top Movies This Month
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

export default TopMonthPage;