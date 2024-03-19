import React from "react";
import {Avatar, Group, Paper, Stack, Text, Title} from "@mantine/core";

function WebHostInfo({hostName, hostPhone}: { hostName: string, hostPhone: string }) {
    return (
        <Stack>
            <Title order={4}>주최자</Title>
            <Paper p={"md"} withBorder>
                <Stack>
                    <Group noWrap>
                        <Avatar radius={"xl"}/>
                        <Text>{hostName}</Text>
                    </Group>
                    <Text>{hostPhone}</Text>
                </Stack>
            </Paper>
        </Stack>
    );
}

export default WebHostInfo;