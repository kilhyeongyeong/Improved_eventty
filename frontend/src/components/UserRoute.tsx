import React from "react";
import {CheckHost} from "../util/CheckHost";
import {Navigate, Outlet} from "react-router-dom";

function UserRoute() {
    const isHost = CheckHost();

    return (
        <>
            {!isHost ? <Outlet/> : <Navigate to={"/"}/>}
        </>
    );
}

export default UserRoute;