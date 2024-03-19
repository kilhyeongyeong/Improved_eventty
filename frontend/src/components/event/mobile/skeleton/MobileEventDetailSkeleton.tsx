import React from "react";
import {Container, Divider, Skeleton, Stack} from "@mantine/core";

function MobileEventDetailSkeleton() {
    return (
        <>
            <Skeleton
                style={{
                    width: "100%",
                    height: 0,
                    paddingBottom: "75%",
                    borderTop: "1px solid #cdcdcd",
                    borderBottom: "1px solid #cdcdcd",
                }}/>
            <Container>
                <Stack spacing={"1.5rem"} style={{margin: "5vh 0", paddingBottom: "5rem"}}>
                    <Stack>
                        <Skeleton height={"1.2rem"} width={"30%"}/>
                        <Skeleton height={"1.8rem"}/>
                        <Skeleton height={"1.4rem"} width={"60%"}/>
                        <Skeleton height={"1rem"}/>
                    </Stack>
                    <Divider/>

                    <Skeleton height={"10rem"}/>
                    <Stack >
                        <Skeleton height={"1.2rem"}/>
                        <Skeleton height={"1.2rem"}/>
                        <Skeleton height={"1.2rem"} width={"50%"}/>
                    </Stack>
                </Stack>
            </Container>
        </>
    );
}

export default MobileEventDetailSkeleton;