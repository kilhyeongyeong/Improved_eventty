import React, {useEffect, useState} from "react";
import {Container, Stack, Tabs} from "@mantine/core";
import {Outlet, useLocation, useNavigate, useParams} from "react-router-dom";
import customStyle from "../../../../styles/customStyle";
import {useRecoilValue} from "recoil";
import {userState} from "../../../../states/userState";

function MobileApplicesLayout() {
    const navigate = useNavigate();
    const {pathname} = useLocation();
    const {eventId} = useParams();
    const {classes} = customStyle();

    const curPath = pathname.split("/").at(-2);
    const [activeTab, setActiveTab] = useState(curPath);
    const handleTabClick = (path: string) => {
        navigate(`${path}/${eventId}`);
    };

    useEffect(() => {
        setActiveTab(curPath);
    }, [curPath]);

    return (
        <Container style={{paddingTop: "2vh", paddingBottom: "10vh"}}>
            <Tabs value={activeTab}
                  className={classes["tabs-primary"]}
                  style={{marginTop: "1vh"}}>
                <Stack>
                    <Tabs.List>
                        <Tabs.Tab value={"all"} onClick={() => handleTabClick("all")}>전체</Tabs.Tab>
                        <Tabs.Tab value={"payment"} onClick={() => handleTabClick("payment")}>결제 완료</Tabs.Tab>
                        <Tabs.Tab value={"canceled"} onClick={() => handleTabClick("canceled")}>예약 취소</Tabs.Tab>
                    </Tabs.List>

                    <Stack>
                        <Outlet/>
                    </Stack>
                </Stack>
            </Tabs>
        </Container>
    );
}

export default MobileApplicesLayout;