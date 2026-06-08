import { Link } from "react-router-dom";

function Navbar() {

    return (
        <nav>

            <Link to="/">
                Home
            </Link>

            {" | "}

            <Link to="/top/day">
                Top Today
            </Link>

            {" | "}

            <Link to="/top/month">
                Top Month
            </Link>

        </nav>
    );
}

export default Navbar;