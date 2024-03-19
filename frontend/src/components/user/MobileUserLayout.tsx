import React from "react";
import {Container, Stack, Tabs} from "@mantine/core";
import {Outlet, useLocation, useNavigate} from "react-router-dom";
import customStyle from "../../styles/customStyle";
import {useRecoilValue} from "recoil";
import {userState} from "../../states/userState";

function MobileUserLayout() {
    const navigate = useNavigate();
    const {pathname} = useLocation();
    const {classes} = customStyle();
    const userStateValue = useRecoilValue(userState);

    const activeTab = pathname.split("/").pop();
    const handleTabClick = (path:string) => {
        navigate(`${path}`);
    };

    return (
        <Container style={{paddingTop: "2vh", paddingBottom: "10vh"}}>
            <Tabs value={activeTab}
                  className={classes["tabs-primary"]}
                  style={{marginTop:"1vh"}}>
                <Stack>
                    <Tabs.List>
                        <Tabs.Tab value={"profile"} onClick={() => handleTabClick("profile")}>내 정보</Tabs.Tab>
                        {userStateValue.isHost ?
                            <Tabs.Tab value={"events"} onClick={() => handleTabClick("events")}>주최 내역</Tabs.Tab> :
                            <Tabs.Tab value={"bookings"} onClick={() => handleTabClick("bookings")}>예약 내역</Tabs.Tab>
                        }
                    </Tabs.List>

                    <Tabs.Panel value={"profile"}>
                        <Outlet/>
                    </Tabs.Panel>
                    <Tabs.Panel value={"events"}>
                        <Outlet/>
                    </Tabs.Panel>
                    <Tabs.Panel value={"bookings"}>
                        <Outlet/>
                    </Tabs.Panel>
                </Stack>
            </Tabs>
        </Container>
    );
}

export default MobileUserLayout;