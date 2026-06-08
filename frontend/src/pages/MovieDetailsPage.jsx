import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import { getMovie } from "../services/movieService";

import "../styles/MovieDetailsPage.css";

function MovieDetailsPage() {

    const { id } = useParams();

    const [movie, setMovie] = useState(null);

    useEffect(() => {

        async function loadMovie() {

            const data = await getMovie(id);

            setMovie(data);
        }

        loadMovie();

    }, [id]);

    if (!movie) {
        return <h2>Loading...</h2>;
    }

    return (
    <div className="details-page">

        <img
            className="details-poster"
            src={movie.poster}
            alt={movie.title}
        />

        <div className="details-info">

            <h1 className="details-title">
                {movie.title}
            </h1>

            <p><strong>Year:</strong> {movie.year}</p>
            <p><strong>Released:</strong> {movie.released}</p>
            <p><strong>Runtime:</strong> {movie.runtime}</p>
            <p><strong>Rated:</strong> {movie.rated?.title}</p>
            <p><strong>Awards:</strong> {movie.awards}</p>

            <div className="details-section">

                <h3>Plot</h3>

                <p>{movie.plot}</p>

            </div>

            <div className="details-section">

                <h3>Genres</h3>

                <ul className="details-list">
                    {movie.genres?.map(genre => (
                        <li key={genre.id}>
                            {genre.title}
                        </li>
                    ))}
                </ul>

            </div>

            <div className="details-section">

                <h3>Actors</h3>

                <ul className="details-list">
                    {movie.actors?.map(actor => (
                        <li key={actor.id}>
                            {actor.firstName} {actor.lastName}
                        </li>
                    ))}
                </ul>

            </div>

            <div className="details-section">

                <h3>Directors</h3>

                <ul className="details-list">
                    {movie.directors?.map(director => (
                        <li key={director.id}>
                            {director.firstName} {director.lastName}
                        </li>
                    ))}
                </ul>

            </div>

        </div>

    </div>
);
            }

export default MovieDetailsPage;