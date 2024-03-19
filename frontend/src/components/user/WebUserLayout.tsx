import React, {useEffect, useMemo, useState} from "react";
import {Box, Container, Divider, Flex, Grid, Stack, Title, UnstyledButton} from "@mantine/core";
import {Link, Outlet, useLocation} from "react-router-dom";
import {CheckHost} from "../../util/CheckHost";

const TABS = [
    {
        key: "profile",
        label: "내 정보",
        path: "profile",
    },
    {
        key: "events",
        label: "주최 내역",
        path: "events",
    },
    {
        key: "bookings",
        label: "예약 내역",
        path: "bookings",
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

function WebUserLayout() {
    const isHost = CheckHost();
    const {pathname} = useLocation();
    const curPath = pathname.split("/").pop();
    const [activeTab, setActiveTab] = useState(curPath);

    const filteredTabs = useMemo(() => {
        return TABS.filter((item) => (
            (isHost && item.key !== "bookings") ||
            (!isHost && item.key !== "events")
        ));
    }, [isHost]);

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
                        {filteredTabs.map((item) => (
                            <Tab key={item.key} label={item.label} path={item.path} isActive={activeTab === item.key}/>
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

export default WebUserLayout;