import ApiService from '../api/ApiService'
import { Link, useNavigate } from 'react-router-dom';

export const Navbar = () => {
    const isAuthenticated = ApiService.isAuthenticated();
    const navigate = useNavigate();

    const handleLogout = async () => {
        const isLogout = window.confirm('Are you sure you want to logout?');

        if (isLogout) {
            ApiService.logout();
            navigate('/login');
        }

    };


    return (
        <nav className="navbar">
            <div className="logo">
                <Link to="/" className="logo-link">
                    spring tasks
                </Link>
            </div>

            <div className="desktop-nav">
                { isAuthenticated ? (
                    <>
                        <Link to="/tasks" className="nav-link"> My Tasks</Link>
                        <button onClick={ handleLogout } className="nav-button">
                            Logout
                        </button>
                    </>
                ) : (
                    <>
                        <Link to="/login" className="nav-link">Login</Link>
                        <Link to="/register" className="nav-link">Register</Link>

                    </>

                ) }
            </div>
        </nav>
    );
};

export default Navbar;
