import React from "react";
import {Button, Group, Stack, Text} from "@mantine/core";
import {modals} from "@mantine/modals";
import customStyle from "../../styles/customStyle";
import {useFetch} from "../../util/hook/useFetch";

function EventDeleteModal({id}:{id:number}) {
    const {classes} = customStyle();
    const {deleteEventFetch} = useFetch();

    return (
        <Stack style={{padding: "1.5rem"}}>
            <Text>정말로 삭제하시겠습니까?</Text>
            <Group grow>
                <Button onClick={() => modals.closeAll()} className={classes["btn-gray-outline"]}>아니오</Button>
                <Button onClick={() => deleteEventFetch(id)} color={"red"}>예</Button>
            </Group>
        </Stack>
    );
}

export default EventDeleteModal;