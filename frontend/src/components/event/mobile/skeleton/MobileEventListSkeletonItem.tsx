import React from "react";
import {Grid, Skeleton, Stack} from "@mantine/core";

function MobileEventListSkeletonItem() {
    return (
        <Grid>
            <Grid.Col span={5}>
                <Skeleton
                    radius={"0.5rem"}
                    style={{
                        width: "100%",
                        height: 0,
                        paddingBottom: "75%",
                    }}/>
            </Grid.Col>
            <Grid.Col span={"auto"}>
                <Stack spacing={"0.5rem"}>
                    <Skeleton height={"1.2rem"} width={"60%"}/>
                    <Skeleton height={"1.5rem"}/>
                    <Skeleton height={"1rem"}/>
                </Stack>
            </Grid.Col>
        </Grid>
    );
}

export default MobileEventListSkeletonItem;