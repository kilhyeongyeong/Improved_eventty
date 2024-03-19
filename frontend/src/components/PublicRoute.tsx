import { useRecoilValue } from 'recoil';
import { loginState } from '../states/loginState';
import {Navigate, Outlet} from 'react-router-dom';

function PublicRoute() {
    const isLoggedIn = useRecoilValue(loginState);

    return isLoggedIn ? <Navigate to={"/"} /> : <Outlet />
}

export default PublicRoute;