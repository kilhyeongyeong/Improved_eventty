import React from "react";
import {Skeleton, Stack} from "@mantine/core";

function WebEventListSkeletonItem() {
    return (
        <Stack>
            <Skeleton
                radius={"0.5rem"}
                style={{
                    width: "100%",
                    height: 0,
                    paddingBottom: "75%",
                }}/>

            <Stack spacing={"0.7rem"}>
                <Skeleton height={"1.2rem"} width={"40%"}/>
                <Skeleton height={"1.5rem"}/>
                <Skeleton height={"1rem"}/>
            </Stack>
        </Stack>
    );
}

export default WebEventListSkeletonItem;