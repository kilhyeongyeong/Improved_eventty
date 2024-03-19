import React, {useEffect} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import {useFetch} from "../../util/hook/useFetch";
import {ISocialLogin} from "../../types/IUser";

function NaverLogin() {
    const {search} = useLocation();
    const {naverLoginFetch} = useFetch();
    const code:ISocialLogin = {
        code: search.split("=")[1].split("&")[0],
    }

    useEffect(() => {
        naverLoginFetch(code);
    }, []);

    return (
        <>

        </>
    );
}

export default NaverLogin;