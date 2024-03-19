import React from "react";
import {CheckXsSize} from "../../util/CheckMediaQuery";
import MobileProfile from "../../components/user/mobile/MobileProfile";
import WebProfile from "../../components/user/web/WebProfile";

function Profile() {
    const isMobile = CheckXsSize();

    return (
        <>
            {isMobile ? <MobileProfile/> : <WebProfile/>}
        </>
    );
}

export default Profile;