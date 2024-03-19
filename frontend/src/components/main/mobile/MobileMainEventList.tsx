import React from "react";
import {IEvent} from "../../../types/IEvent";
import {SimpleGrid} from "@mantine/core";
import MobileEventItem from "../../event/mobile/MobileEventItem";

function MobileMainEventList({data}: { data: IEvent[] }) {

    const items = data.map((item) => {
        if (item.isDeleted) return;

        return (
            <MobileEventItem item={item} badge={false}/>
        )
    });

    return (
        <SimpleGrid cols={1}>
            {items}
        </SimpleGrid>
    );
}

export default MobileMainEventList;