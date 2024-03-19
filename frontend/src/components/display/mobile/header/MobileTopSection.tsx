import React from "react";
import {Container, Header} from "@mantine/core";
import customStyle from "../../../../styles/customStyle";
import {useLocation} from "react-router-dom";
import EventDetailHeader from "./EventDetailHeader";
import MobileHeader from "./MobileHeader";

function MobileTopSection() {
    const HEADER_HEIGHT = "6vh";
    const {pathname} = useLocation();
    const isEventPath = pathname.includes("/event/");
    const {classes} = customStyle();

    return (
        <Header height={HEADER_HEIGHT} className={`${classes["header"]} ${isEventPath && "mobile-event-detail"}`}>
            <Container style={{
                height: "100%",
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
            }}>
                {isEventPath ? <EventDetailHeader/> : <MobileHeader/>}
            </Container>
        </Header>
    );
}

export default MobileTopSection;