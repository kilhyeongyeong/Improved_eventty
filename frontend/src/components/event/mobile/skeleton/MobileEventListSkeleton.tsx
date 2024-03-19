import React from "react";
import {SimpleGrid, Stack} from "@mantine/core";
import MobileEventListSkeletonItem from "./MobileEventListSkeletonItem";

function MobileEventListSkeleton() {
    return (
        <Stack style={{marginBottom: "2rem"}}>
            <SimpleGrid
                cols={1}
                verticalSpacing={"md"}
                style={{paddingBottom: "10vh"}}>
                <MobileEventListSkeletonItem/>
                <MobileEventListSkeletonItem/>
                <MobileEventListSkeletonItem/>
            </SimpleGrid>
        </Stack>
    );
}

export default MobileEventListSkeleton;