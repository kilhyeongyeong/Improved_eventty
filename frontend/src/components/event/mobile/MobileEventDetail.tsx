import React, {useMemo} from "react";
import {Container, Divider, Paper, Stack, Text, Title, UnstyledButton} from "@mantine/core";
import {IEventDetail} from "../../../types/IEvent";
import {useNavigate, useRouteLoaderData} from "react-router-dom";
import MobileTicketInfo from "./MobileTicketInfo";
import {useRecoilValue} from "recoil";
import {eventTicketDrawerState} from "../../../states/eventTicketDrawerState";
import WebHostInfo from "../web/WebHostInfo";
import DOMPurify from "dompurify";
import {CATEGORY_LIST} from "../../../util/const/categoryList";
import EventDetailNavBar from "../../display/mobile/navbar/EventDetailNavBar";

function MobileEventDetail() {
    const navigate = useNavigate();
    const eventTicketDrawerValue = useRecoilValue(eventTicketDrawerState);
    const DATA = useRouteLoaderData("event") as IEventDetail;

    const eventStartAt = useMemo(() => new Date(DATA.eventStartAt), [DATA.eventStartAt]);
    const eventEndtAt = useMemo(() => new Date(DATA.eventEndAt), [DATA.eventEndAt]);

    return (
        <>
            <Paper radius={0}
                   style={{
                       width: "100%",
                       height: 0,
                       paddingBottom: "75%",
                       backgroundImage: `url(${process.env["REACT_APP_NCLOUD_IMAGE_PATH"]}/${DATA.image})`,
                       backgroundRepeat: "no-repeat",
                       backgroundPosition: "center",
                       backgroundSize: "cover",
                       borderTop: "1px solid #cdcdcd",
                       borderBottom: "1px solid #cdcdcd",
                       opacity: DATA.isActive ? 1 : 0.5,
                   }}/>
            <Container>
                <Stack spacing={"1.5rem"} style={{margin: "5vh 0", paddingBottom: "5rem"}}>
                    <Stack>
                        <UnstyledButton onClick={() => navigate(`/events/category/${DATA.category}`)}>
                            <Title order={4} color={"var(--primary)"}>{CATEGORY_LIST[DATA.category]}</Title>
                        </UnstyledButton>
                        <Title order={2}>{DATA.title}</Title>
                        <Title order={4}>
                            {`${eventStartAt.getFullYear()}년 `}
                            {`${eventStartAt.getMonth()+1}월 `}
                            {`${eventStartAt.getDate()}일 `}
                            {!((eventStartAt.getFullYear() === eventEndtAt.getFullYear()) && (eventStartAt.getMonth() === eventEndtAt.getMonth()) && ((eventStartAt.getDate() === eventEndtAt.getDate())))
                                && `${eventStartAt.getHours()}시
                                ${eventStartAt.getMinutes() !== 0 ? `${eventStartAt.getMinutes()}분` : ""}`}
                        </Title>
                        <Title order={4}>
                            {`${!((eventStartAt.getFullYear() === eventEndtAt.getFullYear()) && (eventStartAt.getMonth() === eventEndtAt.getMonth()) && ((eventStartAt.getDate() === eventEndtAt.getDate())))
                                ? `~ ${eventEndtAt.getFullYear()}년  
                                    ${eventEndtAt.getMonth()+1}월 
                                    ${eventEndtAt.getDate()}일 
                                    ${eventEndtAt.getHours()}시 
                                    ${eventEndtAt.getMinutes() !== 0 ? `${eventEndtAt.getMinutes()}분` : ""} `
                                : `${eventStartAt.getHours()}시 
                                    ${eventEndtAt.getMinutes() !== 0 ? `${eventEndtAt.getMinutes()}분` : ""}
                                    ~ ${eventEndtAt.getHours()}시 
                                    ${eventEndtAt.getMinutes() !== 0 ? `${eventEndtAt.getMinutes()}분` : ""}`}`}
                        </Title>
                        <Text color={"gray"}>{DATA.location}</Text>
                    </Stack>
                    <Divider/>

                    <WebHostInfo hostName={DATA.hostName} hostPhone={DATA.hostPhone}/>
                    {/* XSS 방지 */}
                    <div dangerouslySetInnerHTML={{__html: DOMPurify.sanitize(DATA.content)}}></div>
                </Stack>
            </Container>
            <MobileTicketInfo open={eventTicketDrawerValue} tickets={DATA.tickets} isActive={DATA.isActive}/>
            <EventDetailNavBar eventId={DATA.id} hostId={DATA.hostId} isActive={DATA.isActive}/>
        </>
    );
}

export default React.memo(MobileEventDetail);