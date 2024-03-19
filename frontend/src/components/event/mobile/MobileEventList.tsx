import React from "react";
import {SimpleGrid} from "@mantine/core";
import {useLoaderData} from "react-router-dom";
import {IEvent} from "../../../types/IEvent";
import EventsError from "../../../exception/EventsError";
import MobileEventItem from "./MobileEventItem";

function MobileEventList() {
    const DATA = useLoaderData() as IEvent[];

    if (DATA.length < 1) {
        return (
            <EventsError/>
        );
    }

    const items = DATA?.map((item, idx) => {
        if (item.isDeleted) return;

        return (
            <MobileEventItem key={idx} item={item} badge={false}/>
        )
    });

    return (
        <SimpleGrid
            cols={1}
            verticalSpacing={"md"}
            style={{paddingBottom: "10vh"}}>
            {items}
        </SimpleGrid>
    );
}

export default MobileEventList;