import React from "react";
import {useMantineTheme} from "@mantine/core";
import {useMediaQuery} from "react-responsive";
import WebEventDetail from "../../components/event/web/WebEventDetail";
import MobileEventDetail from "../../components/event/mobile/MobileEventDetail";

function EventDetail() {
    const mobile = useMediaQuery({query: `(max-width:${useMantineTheme().breakpoints.xs})`});

    return (
        <>
            {mobile ? <MobileEventDetail/> : <WebEventDetail/>}
        </>
    );
}

export default EventDetail;