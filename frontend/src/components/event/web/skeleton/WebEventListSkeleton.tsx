import React from "react";
import {SimpleGrid} from "@mantine/core";
import WebEventListSkeletonItem from "./WebEventListSkeletonItem";

function WebEventListSkeleton() {
    return (
        <SimpleGrid
            cols={3}
            spacing={"md"}
            verticalSpacing={"3rem"}
            style={{padding: "5vh 0"}}
        >
            <WebEventListSkeletonItem/>
            <WebEventListSkeletonItem/>
            <WebEventListSkeletonItem/>
            <WebEventListSkeletonItem/>
            <WebEventListSkeletonItem/>
            <WebEventListSkeletonItem/>
        </SimpleGrid>
    );
}

export default WebEventListSkeleton;