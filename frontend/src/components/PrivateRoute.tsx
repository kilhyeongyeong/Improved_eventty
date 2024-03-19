import {Navigate, Outlet, useLocation} from 'react-router-dom';
import {CheckLogin} from "../util/CheckLogin";

function PrivateRoute() {
    const {pathname} = useLocation();

    return CheckLogin() ? <Outlet /> : <Navigate to={"/login"} state={pathname}/>
}

export default PrivateRoute;