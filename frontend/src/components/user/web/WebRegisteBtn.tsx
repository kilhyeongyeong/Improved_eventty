import React from "react";
import {Badge, Button, Group, Paper, Stack, Text, Title, UnstyledButton} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import {useNavigate} from "react-router-dom";
import {CheckHost} from "../../../util/CheckHost";
import {IEvent} from "../../../types/IEvent";
import {useModal} from "../../../util/hook/useModal";

const eventState = {
    open: {
        label: "게시중",
        color: "indigo.7",
        variant: "light",
    },
    close: {
        label: "예약 종료",
        color: "indigo.7",
        variant: "outline",
    },
}

function WebRegisteBtn({data}: { data: IEvent }) {
    const {classes} = customStyle();
    const {eventDeleteModal} = useModal();
    const navigate = useNavigate();
    const state = data.isActive ? "open" : "close";
    const eventStartAt = new Date(data.eventStartAt);
    const eventEndAt = new Date(data.eventEndAt);

    return (
        <UnstyledButton
            onClick={() => navigate(`/event/${data.id}`)}
            style={{border: "1px solid #cdcdcd", padding: "1.2rem", borderRadius: "0.3rem"}}>
            <Group noWrap position={"apart"} style={{opacity: state === "close" ? "0.3" : "",}}>
                <Group noWrap>
                    <Paper withBorder
                           style={{
                               backgroundImage: `url(${process.env["REACT_APP_NCLOUD_IMAGE_PATH"]}/${data.image})`,
                               backgroundPosition: "center",
                               backgroundRepeat: "no-repeat",
                               backgroundSize: "cover",
                               width: 120,
                               height: 90,
                           }}>
                    </Paper>
                    <Stack>
                        <Title order={4} lineClamp={1}>{data.title}</Title>
                        <Group noWrap>
                            <Badge size={"lg"}
                                   radius={"lg"}
                                   color={eventState[state]["color"]}
                                   variant={eventState[state]["variant"]}
                                   style={{width: "5rem"}}>
                                {eventState[state]["label"]}
                            </Badge>
                            <Text fz={"sm"}>
                                {`${eventStartAt.getFullYear()}년
                                ${eventStartAt.getMonth()}월
                                ${eventStartAt.getDate()}일`}
                                {` ~ ${eventEndAt.getFullYear()}년
                                ${eventEndAt.getMonth()}월
                                ${eventEndAt.getDate()}일`}
                            </Text>
                        </Group>
                    </Stack>
                </Group>
                {CheckHost() &&
                    <Stack spacing={"0.5rem"}>
                        <Button onClick={(e) => {
                            e.stopPropagation();
                            navigate(`/users/events/applices/all/${data.id}`)
                        }}
                                className={classes["btn-primary"]}>
                            신청내역
                        </Button>
                        <Group noWrap>
                            <Button onClick={(e) => {
                                e.stopPropagation();
                                navigate(`/update/${data.id}`)
                            }}
                                    className={classes["btn-gray-outline"]}>
                                수정
                            </Button>
                            <Button variant={"outline"}
                                    color={"red"}
                                    onClick={(e) => {
                                        e.stopPropagation();
                                        eventDeleteModal(data.id);
                                    }}>
                                삭제
                            </Button>
                        </Group>
                    </Stack>
                }
            </Group>
        </UnstyledButton>
    );
}

export default WebRegisteBtn;