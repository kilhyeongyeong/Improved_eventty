import React from "react";
import {Container} from "@mantine/core";
import MobileEvents from "../components/event/mobile/MobileEvents";
import WebEvents from "../components/event/web/WebEvents";
import {CheckXsSize} from "../util/CheckMediaQuery";

function Events() {
    const isXsSize = CheckXsSize();

    return (
        <Container style={{margin: "5vh auto"}}>
            {isXsSize ? <MobileEvents/> : <WebEvents/>}
        </Container>
    );
}

export default Events;