import { useState } from "react";

function SearchBar({ onSearch }) {

    const [title, setTitle] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();

        onSearch(title);
    };

    return (
        <form onSubmit={handleSubmit}>

            <input
                type="text"
                placeholder="Search movie..."
                value={title}
                onChange={(e) => setTitle(e.target.value)}
            />

            <button type="submit">
                Search
            </button>

        </form>
    );
}

export default SearchBar;