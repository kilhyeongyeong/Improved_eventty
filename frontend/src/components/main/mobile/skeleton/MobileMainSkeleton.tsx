import React from "react";
import {Container, Skeleton, Stack} from "@mantine/core";
import MobileMainSkeletonItem from "./MobileMainSkeletonItem";

const CAROUSEL_HEIGHT = "100vw";
const CAROUSEL_MIN_HEIGHT = "200px";
const CAROUSEL_MAX_HEIGHT = "400px";

function MobileMainSkeleton() {
    return (
        <>
            <Skeleton style={{
                height: CAROUSEL_HEIGHT,
                minHeight: CAROUSEL_MIN_HEIGHT,
                maxHeight: CAROUSEL_MAX_HEIGHT,
            }}/>
            <Container>
                <Skeleton style={{
                    width:"100%",
                    height: "80px",
                    margin: "2.5rem 0"
                }}/>

                <Stack spacing={"5rem"} style={{marginBottom: "5rem"}}>
                    <Stack>
                        <Skeleton style={{
                            width: "60%",
                            height: "2.5rem",
                        }}/>
                        <MobileMainSkeletonItem/>
                        <MobileMainSkeletonItem/>
                        <MobileMainSkeletonItem/>
                        <MobileMainSkeletonItem/>
                        <MobileMainSkeletonItem/>
                    </Stack>
                </Stack>
            </Container>
        </>
    );
}

export default MobileMainSkeleton;