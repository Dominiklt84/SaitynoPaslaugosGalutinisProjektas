import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import { getMovie } from "../services/movieService";

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
        <div>

            <img
                src={movie.poster}
                alt={movie.title}
                width="300"
            />

            <h1>{movie.title}</h1>

            <p><strong>Year:</strong> {movie.year}</p>

            <p><strong>Released:</strong> {movie.released}</p>

            <p><strong>Runtime:</strong> {movie.runtime}</p>

            <p><strong>Rated:</strong> {movie.rated?.title}</p>

            <p><strong>Awards:</strong> {movie.awards}</p>

            <p><strong>Plot:</strong></p>
            <p>{movie.plot}</p>

            <h3>Genres</h3>

            <ul>
                {movie.genres?.map(genre => (
                    <li key={genre.id}>
                        {genre.title}
                    </li>
                ))}
            </ul>

            <h3>Actors</h3>

            <ul>
                {movie.actors?.map(actor => (
                    <li key={actor.id}>
                        {actor.firstName} {actor.lastName}
                    </li>
                ))}
            </ul>

            <h3>Directors</h3>

            <ul>
                {movie.directors?.map(director => (
                    <li key={director.id}>
                        {director.firstName} {director.lastName}
                    </li>
                ))}
            </ul>

            <h3>Writers</h3>
            <ul>
            {movie.writers?.map(writer => (
                <li key={writer.id}>
                {writer.firstName} {writer.lastName}
                </li>
            ))}
            </ul>

            <h3>Countries</h3>
            <ul>
            {movie.countries?.map(country => (
                <li key={country.id}>
                {country.name}
                </li>
            ))}
            </ul>

            <h3>Languages</h3>
            <ul>
            {movie.languages?.map(language => (
                <li key={language.id}>
                {language.name}
                </li>
            ))}
            </ul>

            <h3>Ratings</h3>
            <ul>
            {movie.ratings?.map(rating => (
                <li key={rating.id}>
                {rating.source}: {rating.value}
                </li>
            ))}
            </ul>

                    </div>
                );
            }

export default MovieDetailsPage;