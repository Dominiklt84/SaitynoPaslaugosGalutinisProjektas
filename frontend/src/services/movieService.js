const API_URL = "http://localhost:8099/api/movies";

export async function searchMovie(title) {

    const response = await fetch(
        `${API_URL}/omdb?title=${title}`
    );

    const data = await response.json();

    console.log("API RESPONSE:", data);

    return data;
}

export async function getMovie(id) {

    const response = await fetch(
        `${API_URL}/${id}`
    );

    const data = await response.json();

    return data;
}

export async function getTopTodayMovies() {

    const response = await fetch(
        "http://localhost:8099/api/movies/top/day"
    );

    return await response.json();
}

export async function getTopMonthMovies() {

    const response = await fetch(
        "http://localhost:8099/api/movies/top/month"
    );

    return await response.json();
}

export async function getAllMovies() {

    const response =
        await fetch("http://localhost:8099/api/movies");

    const data = await response.json();

    return data._embedded?.movieList || [];
}