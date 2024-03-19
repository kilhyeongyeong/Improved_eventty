import React from "react";
import {Skeleton, Stack} from "@mantine/core";

function WebMainSkeletonItem() {
    return (
        <Stack>
            <Skeleton style={{
                width: "100%",
                height: 0,
                paddingBottom: "75%",
            }}/>
            <Stack spacing={"1rem"}>
                <Skeleton height={"1.2rem"} width={"50%"}/>
                <Skeleton height={"1.6rem"} width={"100%"}/>
                <Skeleton height={"0.9rem"} width={"100%"}/>
            </Stack>
        </Stack>
    );
}

export default WebMainSkeletonItem;