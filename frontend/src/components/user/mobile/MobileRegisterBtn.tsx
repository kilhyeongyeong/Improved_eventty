import React from "react";
import {
    Button,
    Group,
    Paper,
    Stack,
    Text,
    Title,
    UnstyledButton
} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import {useLocation, useNavigate} from "react-router-dom";
import {IEvent} from "../../../types/IEvent";
import {useModal} from "../../../util/hook/useModal";

function MobileRegisterBtn({data}: { data: IEvent }) {
    const {classes} = customStyle();
    const {eventDeleteModal} = useModal();
    const {pathname} = useLocation();
    const navigate = useNavigate();
    const state = data.isActive ? "open" : "close";
    const eventStartAt = new Date(data.eventStartAt);
    const eventEndAt = new Date(data.eventEndAt);

    return (
        <UnstyledButton
            onClick={() => navigate(`/event/${data.id}`)}
            style={{border: "1px solid #cdcdcd", padding: "1.2rem", borderRadius: "0.3rem"}}>
            <Stack style={{opacity: state === "close" ? "0.3" : "",}}>
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
                        <Title order={4}>{data.title}</Title>
                        <Text fz={"sm"} style={{whiteSpace: "pre-line"}}>
                            {`${eventStartAt.getFullYear()}년 ${eventStartAt.getMonth()}월 ${eventStartAt.getDate()}일 
                                ~ ${eventEndAt.getFullYear()}년 ${eventEndAt.getMonth()}월 ${eventEndAt.getDate()}일`}
                        </Text>
                    </Stack>
                </Group>
                <Group grow>
                    <Button onClick={(e) => {
                        e.stopPropagation();
                        navigate(`/users/events/applices/all/${data.id}`)
                    }}
                            className={classes["btn-primary"]}>
                        신청내역
                    </Button>
                    <Group noWrap grow onClick={(e) => e.stopPropagation()}>
                        <Button onClick={(e) => {
                            e.stopPropagation();
                            navigate(`/update/${data.id}`, {state: pathname});
                        }}
                                className={classes["btn-gray-outline"]}>
                            수정
                        </Button>
                        <Button variant={"outline"}
                                color={"red"}
                                onClick={(e) => {
                                    eventDeleteModal(data.id);
                                }}>
                            삭제
                        </Button>
                    </Group>
                </Group>
            </Stack>
        </UnstyledButton>
    )
        ;
}

export default MobileRegisterBtn;