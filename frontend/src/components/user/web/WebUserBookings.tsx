import React from "react";
import {Divider, Stack, Title} from "@mantine/core";
import {useLoaderData} from "react-router-dom";
import {IEventUserBookings} from "../../../types/IEvent";
import WebApplyBtn from "./WebApplyBtn";

function WebUserBookings() {
    const DATA = useLoaderData() as IEventUserBookings[];

    const items = DATA.map(item => (
        <WebApplyBtn data={item}/>
    ));

    return (
        <>
            <Stack>
                <Title order={3}>예약 내역</Title>
                <Divider/>
                {items}
            </Stack>
        </>
    );
}

export default WebUserBookings;