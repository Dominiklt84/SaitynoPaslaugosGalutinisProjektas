import { Link } from "react-router-dom";

import "../styles/MovieCard.css";

function MovieCard({ movie }) {

    return (
        <div className="movie-card">

            <img
                src={movie.poster}
                alt={movie.title}
            />

            <h2>{movie.title}</h2>

            <p>{movie.year}</p>

            <Link to={`/movie/${movie.id}`}>
                View Details
            </Link>

        </div>
    );
}

export default MovieCard;