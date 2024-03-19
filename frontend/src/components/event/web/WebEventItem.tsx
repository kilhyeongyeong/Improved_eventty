import React from "react";
import {Badge, Group, Paper, Stack, Text, Title} from "@mantine/core";
import {Link} from "react-router-dom";
import {IEvent} from "../../../types/IEvent";

function WebEventItem({item, badge}: { item: IEvent, badge: boolean }) {
    const startAt = new Date(item.eventStartAt);
    const endtAt = new Date(item.eventEndAt);

    return (
        <Link to={`/event/${item.id}`} key={item.id}>
            <Stack>
                <Group style={{alignItems: "flex-start"}}>
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

                    <Stack spacing={"0.5rem"}>
                        <Group spacing={"xs"} style={{display: badge ? "" : "none"}}>
                            <Badge radius={"sm"} color={"red"}>D-00</Badge>
                            <Badge radius={"sm"} color={"lime"}>인기</Badge>
                            <Badge radius={"sm"} color={"indigo"}>신규</Badge>
                        </Group>
                        <Title size={"0.9rem"} color={"var(--primary)"}>
                            {`${startAt.getMonth() + 1}월 ${startAt.getDate()}일`}
                            {!((startAt.getMonth() === endtAt.getMonth()) && (startAt.getDate() === endtAt.getDate())) &&
                                ` ~ ${endtAt.getMonth() + 1}월 ${endtAt.getDate()}일`}
                        </Title>
                        <Title order={4}>{item.title}</Title>
                        <Text fz={"sm"} c={"gray"}>{item.location}</Text>
                    </Stack>
                </Group>
            </Stack>
        </Link>
    );
}

export default WebEventItem;