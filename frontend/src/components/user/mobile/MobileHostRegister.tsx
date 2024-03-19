import React from "react";
import {Stack, Title} from "@mantine/core";
import MobileRegisterBtn from "./MobileRegisterBtn";
import {useLoaderData} from "react-router-dom";
import {IEvent} from "../../../types/IEvent";

function MobileHostRegister() {
    const DATA = useLoaderData() as IEvent[];

    const items = DATA.map(item => (
        <MobileRegisterBtn data={item}/>
    ));

    return (
        <Stack>
            <Title order={3}>주최 내역</Title>
            {items}
        </Stack>
    );
}

export default MobileHostRegister;