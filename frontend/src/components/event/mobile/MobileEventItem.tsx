import React from "react";
import {Badge, Grid, Group, Paper, Stack, Text, Title} from "@mantine/core";
import {Link} from "react-router-dom";
import {IEvent} from "../../../types/IEvent";

function MobileEventItem({item, badge}: { item: IEvent, badge: boolean }) {
    const startAt = new Date(item.eventStartAt);
    const endtAt = new Date(item.eventEndAt);

    return (
        <Link to={`/event/${item.id}`} key={item.id}>
            <Grid>
                <Grid.Col span={5}>
                    <Paper
                        withBorder
                        radius={"0.5rem"}
                        style={{
                            width: "100%",
                            height: 0,
                            paddingBottom: "75%",
                            backgroundImage: `url(${process.env["REACT_APP_NCLOUD_IMAGE_PATH"]}/${item.image})`,
                            backgroundRepeat: "no-repeat",
                            backgroundPosition: "center",
                            backgroundSize: "cover",
                        }}/>
                </Grid.Col>
                <Grid.Col span={"auto"}>
                    <Stack spacing={"0.25rem"}>
                        <Group spacing={"xs"} style={{display: badge ? "" : "none"}}>
                            <Badge radius={"sm"} color={"red"} size={"xs"}>D-00</Badge>
                            <Badge radius={"sm"} color={"lime"}>인기</Badge>
                            <Badge radius={"sm"} color={"indigo"}>신규</Badge>
                        </Group>
                        <Text fz={"0.8rem"} fw={700} color={"var(--primary)"}>
                            {`${startAt.getMonth() + 1}월 ${startAt.getDate()}일`}
                            {!(startAt.getMonth() === endtAt.getMonth()) && (startAt.getDate() === endtAt.getDate()) &&
                                ` ~ ${endtAt.getMonth() + 1}월 ${endtAt.getDate()}일`}
                        </Text>
                        <Title order={4}>{item.title}</Title>
                        <Text fz={"xs"} c={"gray"}>{item.location}</Text>
                    </Stack>
                </Grid.Col>
            </Grid>
        </Link>
    );
}

export default MobileEventItem;