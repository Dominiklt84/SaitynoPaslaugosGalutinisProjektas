import { useEffect, useState } from "react";

import MovieCard from "../components/MovieCard";

import { getTopTodayMovies } from "../services/movieService";

function TopTodayPage() {

    const [movies, setMovies] = useState([]);

    useEffect(() => {

        async function loadMovies() {

            const data =
                await getTopTodayMovies();

            setMovies(data);
        }

        loadMovies();

    }, []);

    return (

        <div>

            <h1>Top Movies Today</h1>

            {movies.map(movie => (

                <MovieCard
                    key={movie.id}
                    movie={movie}
                />

            ))}

        </div>
    );
}

export default TopTodayPage;