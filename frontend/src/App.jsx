import { Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";
import HomePage from "./pages/HomePage";
import MovieDetailsPage from "./pages/MovieDetailsPage";
import TopTodayPage from "./pages/TopTodayPage";
import TopMonthPage from "./pages/TopMonthPage";

function App() {
    return (
        <>
            <Navbar />

            <Routes>

                <Route
                    path="/"
                    element={<HomePage />}
                />

                <Route
                    path="/top/day"
                    element={<TopTodayPage />}
                />

                <Route
                    path="/top/month"
                    element={<TopMonthPage />}
                />

                <Route
                    path="/movie/:id"
                    element={<MovieDetailsPage />}
                />

            </Routes>
        </>
    );
}

export default App;