import { Navigate, useLocation } from 'react-router-dom';
import ApiService from '../api/ApiService';

export const AuthRouter = ({ element: Component }) => {
    const location = useLocation();
    return ApiService.isAuthenticated()
        ? Component : (<Navigate to={ `/login` } replace={ true } state={ { from: location } } />
        )
}
