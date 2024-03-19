import React, {useEffect, useState} from "react";
import {Link, Outlet, useLocation, useParams} from "react-router-dom";
import {Box, Container, Divider, Flex, Grid, Stack, Title, UnstyledButton} from "@mantine/core";
import WebApplicesResult from "./WebApplicesResult";

const TABS = [
    {
        key: "all",
        label: "전체",
        path: "all",
    },
    {
        key: "payment",
        label: "결제 완료",
        path: "payment",
    },
    {
        key: "canceled",
        label: "예약 취소",
        path: "canceled",
    },
];

function Tab(item: {key:string, label:string, path:string, isActive:boolean}) {
    return (
        <Flex gap={"0.5rem"}>
            <Box
                style={{
                    height: "auto",
                    width: "5px",
                    background: "var(--primary)",
                    borderRadius: "10rem",
                    visibility: item.isActive ? "inherit" : "hidden",
                }}
            />
            <UnstyledButton
                component={Link}
                to={item.path}
                style={{
                    background: item.isActive ? "#eeeeee" : "",
                    borderRadius: "0.3rem",
                    padding: "0.7rem",
                    width: "100%",
                }}
            >
                {item.label}
            </UnstyledButton>
        </Flex>
    );
}

function WebApplicesLayout() {
    const {eventId} = useParams();
    const {pathname} = useLocation();
    const curPath = pathname.split("/").at(-2);
    const [activeTab, setActiveTab] = useState(curPath);

    useEffect(() => {
        setActiveTab(curPath);
    }, [curPath]);

    return (
        <Container style={{paddingTop: "5vh", paddingBottom: "10vh"}}>
            <Grid>
                <Grid.Col span={3}>
                    <Stack>
                        <Title order={3}>마이페이지</Title>
                        <Divider/>
                        {TABS.map((item) => (
                            <Tab key={item.key} label={item.label} path={`${item.path}/${eventId}`} isActive={activeTab === item.key}/>
                        ))}
                    </Stack>
                </Grid.Col>
                <Grid.Col span={9}>
                    <Outlet/>
                </Grid.Col>
            </Grid>
        </Container>
    );
}

export default WebApplicesLayout;