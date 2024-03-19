import React from "react";
import {SimpleGrid} from "@mantine/core";
import {useLoaderData} from "react-router-dom";
import {IEvent} from "../../../types/IEvent";
import WebEventItem from "./WebEventItem";

function WebEventList() {
    const DATA = useLoaderData() as IEvent[];

    const items = DATA?.map((item) => {
        if (item.isDeleted) return;

        return (
            <WebEventItem item={item} badge={false}/>
        );
    });

    return (
        <SimpleGrid
            cols={3}
            spacing={"md"}
            verticalSpacing={"3rem"}
            style={{padding: "5vh 0"}}
        >
            {items}
        </SimpleGrid>
    );
}

export default WebEventList;