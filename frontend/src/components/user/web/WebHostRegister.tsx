import React from "react";
import {Divider, Stack, Title} from "@mantine/core";
import WebRegisteBtn from "./WebRegisteBtn";
import {useLoaderData} from "react-router-dom";
import {IEvent} from "../../../types/IEvent";

function WebHostRegister() {
    const DATA = useLoaderData() as IEvent[];

    const items = DATA.map(item => (
        <WebRegisteBtn data={item}/>
    ));

    return (
        <>
            <Stack>
                <Title order={3}>주최 내역</Title>
                <Divider/>
                {items}
            </Stack>
        </>
    );
}

export default WebHostRegister;