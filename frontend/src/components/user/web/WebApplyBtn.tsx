import React from "react";
import {Badge, Button, Group, Paper, Stack, Text, Title, UnstyledButton} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import {useLocation, useNavigate} from "react-router-dom";
import {IEventUserBookings} from "../../../types/IEvent";
import {useModal} from "../../../util/hook/useModal";

const eventState = {
    open: {
        label: "예약 완료",
        color: "indigo.7",
        variant: "light",
    },
    close: {
        label: "행사 종료",
        color: "indigo.7",
        variant: "outline",
    },
    cancel: {
        label: "예약 취소",
        color: "gray",
        variant: "outline",
    }
}

function WebApplyBtn({data}: { data: IEventUserBookings }) {
    const {classes} = customStyle();
    const {eventApplyCancelModal} = useModal();
    const {pathname} = useLocation();
    const navigate = useNavigate();
    const date = new Date(data.date);
    const state = data.status === "예약 완료" ? "open" : data.status === "예약 취소" ? "cancel" : "close";

    return (
        <UnstyledButton
            style={{border: "1px solid #cdcdcd", padding: "1.2rem", borderRadius: "0.3rem"}}>
            <Stack>
                <Group>
                    <Title order={4}>
                        {`${date.getFullYear()}. ${date.getMonth() + 1}. ${date.getDate()}`}
                    </Title>
                    <Badge size={"lg"}
                           radius={"lg"}
                           color={eventState[state]["color"]}
                           variant={eventState[state]["variant"]}>
                        {eventState[state]["label"]}
                    </Badge>
                </Group>
                <Group noWrap position={"apart"} style={{opacity: data.status !== "예약 완료" ? "0.3" : "",}}>
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
                        <Stack spacing={"0.5rem"}>
                            <Title order={3}>{data.title}</Title>
                            <Text>{`${data.ticketName}`}</Text>
                            <Text>{`${data.ticketPrice.toLocaleString("ko")} 원 x ${data.applicantNum}개`}</Text>
                        </Stack>
                    </Group>
                    {state === "open" &&
                        <Button variant={"outline"}
                                color={"red"}
                                onClick={(e) => {
                                    e.stopPropagation();
                                    eventApplyCancelModal(data.applyId);
                                }}>
                            취소
                        </Button>
                    }
                </Group>
            </Stack>
        </UnstyledButton>
    );
}

export default WebApplyBtn;