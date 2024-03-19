import React from "react";
import {Navigate, Outlet} from "react-router-dom";
import {CheckHost} from "../util/CheckHost";

function HostRoute() {
    const isHost = CheckHost();

    return (
        <>
            {isHost ? <Outlet/> : <Navigate to={"/"}/>}
        </>
    );
}

export default HostRoute;