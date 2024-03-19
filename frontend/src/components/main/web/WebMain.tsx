import React from "react";
import WebCarousel from "./WebCarousel";
import {Container, Stack, Title} from "@mantine/core";
import WebMainEventList from "./WebMainEventList";
import {IEventMain} from "../../../types/IEvent";

function WebMain({data}: { data: IEventMain }) {
    return (
        <>
            <WebCarousel/>
            <Container>
                <Stack spacing={"8rem"} style={{margin: "5rem 0"}}>
                    <Stack>
                        <Title order={2}>인기 상승중인 행사</Title>
                        <WebMainEventList data={data?.Top10Views}/>
                    </Stack>

                    <Stack>
                        <Title order={2}>신규 행사</Title>
                        <WebMainEventList data={data?.Top10CreatedAt}/>
                    </Stack>

                    <Stack>
                        <Title order={2}>마감 임박</Title>
                        <WebMainEventList data={data?.Top10ApplyEndAt}/>
                    </Stack>
                </Stack>
            </Container>
        </>
    );
}

export default WebMain;