import React from "react";
import {Box, Button, Container, Flex} from "@mantine/core";
import customStyle from "../../../../styles/customStyle";

interface IBookingSubmit {
    onSubmit: () => void;
}

function EventBookingNavBar({onSubmit}: IBookingSubmit) {
    const {classes} = customStyle();

    return (
        <Box
            style={{
                zIndex: 1000,
                position: "fixed",
                background: "white",
                height: "8vh",
                width: "100%",
                bottom: "0",
                boxShadow: "2px 0 6px rgba(0, 0, 0, 0.1)",
            }}>
            <Flex style={{height: "100%"}} align={"center"}>
                <Container style={{width: "100%", height: "100%"}}>
                    <Flex align={"center"} style={{height: "100%"}}>
                        <Button className={classes["btn-primary"]}
                                style={{width: "100%", height: "80%"}}
                                onClick={onSubmit}>결제하기</Button>
                    </Flex>
                </Container>
            </Flex>
        </Box>
    );
}

export default EventBookingNavBar;