import React from "react";
import {Container, useMantineTheme} from "@mantine/core";
import Logo from "../common/Logo";
import {useMediaQuery} from "react-responsive";

function Footer() {
    const mobile = useMediaQuery({query: `(max-width:${useMantineTheme().breakpoints.xs})`});

    return (
        <div style={{background: "white", padding: `${mobile ? "2rem 0 6rem" : "2rem 0"}`, borderTop: "1px solid rgba(0, 0, 0, 0.1)"}}>
        <Container>
            <Logo fill={"var(--primary)"} height={"2rem"}/>
        </Container>
        </div>
    );
}

export default Footer;