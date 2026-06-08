import { useEffect, useState } from "react";

import MovieCard from "../components/MovieCard";

import { getTopMonthMovies } from "../services/movieService";

function TopMonthPage() {

    const [movies, setMovies] = useState([]);

    useEffect(() => {

        async function loadMovies() {

            const data =
                await getTopMonthMovies();

            setMovies(data);
        }

        loadMovies();

    }, []);

    return (

        <div>

            <h1>Top Movies This Month</h1>

            {movies.map(movie => (

                <MovieCard
                    key={movie.id}
                    movie={movie}
                />

            ))}

        </div>
    );
}

export default TopMonthPage;