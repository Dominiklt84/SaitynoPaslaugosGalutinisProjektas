import { Link } from "react-router-dom";

import "../styles/Navbar.css";

function Navbar() {

    return (

        <nav className="navbar">

            <div className="navbar-logo">
                Movie Explorer
            </div>

            <div className="navbar-links">

                <Link to="/">
                    Home
                </Link>

                <Link to="/top/day">
                    Top Today
                </Link>

                <Link to="/top/month">
                    Top Month
                </Link>

            </div>

        </nav>

    );
}

export default Navbar;