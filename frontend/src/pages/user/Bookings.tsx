import React from "react";
import {CheckXsSize} from "../../util/CheckMediaQuery";
import MobileUserBookings from "../../components/user/mobile/MobileUserBookings";
import WebUserBookings from "../../components/user/web/WebUserBookings";

function Bookings() {
    const isMobile = CheckXsSize();

    return (
        <>
            {isMobile ? <MobileUserBookings/> : <WebUserBookings/>}
        </>
    );
}

export default Bookings;