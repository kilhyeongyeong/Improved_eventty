import React from "react";
import {Button, Group, Stack, Text} from "@mantine/core";
import {modals} from "@mantine/modals";
import {useFetch} from "../../util/hook/useFetch";
import customStyle from "../../styles/customStyle";

function EventApplyCancelModal({applyId}:{applyId:number}) {
    const {classes} = customStyle();
    const {applyEventCancelFetch} = useFetch();

    return (
        <Stack style={{padding: "1.5rem"}}>
            <Text>정말로 취소하시겠습니까?</Text>
            <Group grow>
                <Button onClick={() => modals.closeAll()} className={classes["btn-gray-outline"]}>아니오</Button>
                <Button onClick={() => applyEventCancelFetch(applyId)} color={"red"}>예</Button>
            </Group>
        </Stack>
    );
}

export default EventApplyCancelModal;