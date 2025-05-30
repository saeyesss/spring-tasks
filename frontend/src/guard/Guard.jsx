import { useLocation, Navigate } from "react-router-dom";
import ApiService from "../api/ApiService.jsx";

export const AuthRoute = ({ element }) => {
    const location = useLocation();

    return ApiService.isAuthenticated() ? (
        element
    ) : (
        <Navigate to="/login" replace state={ { from: location } } />
    );
};
