import React from "react";
import customStyle from "../../styles/customStyle";
import {useFetch} from "../../util/hook/useFetch";
import {Button, Group, Stack, Text} from "@mantine/core";
import {modals} from "@mantine/modals";

function DeleteAccountModal() {
    const {classes} = customStyle();
    const {deleteAccountFetch} = useFetch();

    return (
        <Stack style={{padding: "1.5rem"}}>
            <Text>정말로 탈퇴하시겠습니까?</Text>
            <Group grow>
                <Button onClick={() => modals.closeAll()} className={classes["btn-gray-outline"]}>아니오</Button>
                <Button onClick={() => deleteAccountFetch()} color={"red"}>예</Button>
            </Group>
        </Stack>
    );
}

export default DeleteAccountModal;