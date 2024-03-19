import React from "react";
import {Container, SimpleGrid, Skeleton, Stack} from "@mantine/core";
import WebMainSkeletonItem from "./WebMainSkeletonItem";

const CAROUSEL_HEIGHT = "35vw";
const CAROUSEL_MIN_HEIGHT = "230px";
const CAROUSEL_MAX_HEIGHT = "400px";

function WebMainSkeleton() {
    return (
        <>
            <Skeleton style={{
                height: CAROUSEL_HEIGHT,
                minHeight: CAROUSEL_MIN_HEIGHT,
                maxHeight: CAROUSEL_MAX_HEIGHT,
            }}/>
            <Container>
                <Stack spacing={"8rem"} style={{margin: "5rem 0"}}>
                    <Stack>
                        <Skeleton height={"2.5rem"} width={"25%"}/>
                        <SimpleGrid cols={5}
                                    breakpoints={[{maxWidth: "62rem", cols: 3}]}
                                    verticalSpacing={"2rem"}>
                            <WebMainSkeletonItem/>
                            <WebMainSkeletonItem/>
                            <WebMainSkeletonItem/>
                            <WebMainSkeletonItem/>
                            <WebMainSkeletonItem/>
                        </SimpleGrid>
                    </Stack>
                </Stack>
            </Container>
        </>
    );
}

export default WebMainSkeleton;