import React from "react";
import {Box, Button, Container, Flex, Group} from "@mantine/core";
import customStyle from "../../../../styles/customStyle";
import {useRecoilValue, useSetRecoilState} from "recoil";
import {eventTicketDrawerState} from "../../../../states/eventTicketDrawerState";
import {userState} from "../../../../states/userState";
import {useModal} from "../../../../util/hook/useModal";
import {useLocation, useNavigate} from "react-router-dom";

function EventDetailNavBar({hostId, eventId, isActive}: { hostId: number, eventId: number, isActive:boolean }) {
    const setEventTicketDrawer = useSetRecoilState(eventTicketDrawerState);
    const {classes} = customStyle();
    const userStateValue = useRecoilValue(userState);
    const navigate = useNavigate();
    const {pathname} = useLocation();
    const {eventDeleteModal} = useModal();

    return (
        <Box
            style={{
                zIndex: 1000,
                position: "fixed",
                background: "white",
                height: "8vh",
                width: "100%",
                bottom: "0",
                boxShadow: "2px 0 6px rgba(0, 0, 0, 0.1)",
            }}>
            <Container style={{height: "100%"}}>
                <Flex align={"center"} style={{height: "100%", width: "100%"}}>
                    {userStateValue.isHost && (userStateValue.userId === hostId) ?
                        <Group noWrap style={{height: "80%", width: "100%"}}>
                            <Button variant={"outline"}
                                    color={"red"}
                                    fullWidth
                                    onClick={() => eventDeleteModal(eventId)}
                                    style={{width: "100%", height: "100%"}}>취소하기</Button>
                            <Button fullWidth
                                    onClick={() => navigate(`/update/${eventId}`, {state: pathname})}
                                    className={classes["btn-primary-outline"]}
                                    style={{width: "100%", height: "100%"}}>수정하기</Button>
                        </Group>
                        : userStateValue.isHost || !isActive ?
                            <Button className={`${classes["btn-primary"]} disable`}
                                    style={{width: "100%", height: "80%"}}>예약 불가</Button> :
                            <Button className={classes["btn-primary"]}
                                    style={{width: "100%", height: "80%"}}
                                    onClick={() => setEventTicketDrawer(prev => !prev)}>예약하기</Button>
                    }
                </Flex>
            </Container>
        </Box>
    );
}

export default EventDetailNavBar;