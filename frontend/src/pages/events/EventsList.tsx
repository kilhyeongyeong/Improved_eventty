import React from "react";
import {CheckXsSize} from "../../util/CheckMediaQuery";
import MobileEventList from "../../components/event/mobile/MobileEventList";
import WebEventList from "../../components/event/web/WebEventList";

function EventsList() {
    const isXsSize = CheckXsSize();

    return (
        <>
            {isXsSize ? <MobileEventList/> : <WebEventList/>}
        </>
    );
}

export default EventsList;