import React from 'react';
import {CheckXsSize} from "../../util/CheckMediaQuery";
import MobileBookingTicket from "../../components/ticket/mobile/MobileBookingTicket";
import WebBookingTicket from "../../components/ticket/web/WebBookingTicket";

function EventBooking() {
    const isXsSize = CheckXsSize();

    return (
        <div style={{background: "#f0f0f0", padding: "5vh 0"}}>
            {isXsSize ? <MobileBookingTicket/> : <WebBookingTicket/>}
        </div>
    )
}

export default EventBooking;