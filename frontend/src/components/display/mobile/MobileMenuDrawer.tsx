import React from "react";
import {Avatar, Button, Container, Divider, Drawer, Flex, Group, Stack, Text, UnstyledButton} from "@mantine/core";
import {Link, useLocation, useNavigate} from "react-router-dom";
import {IconHome, IconPlus, IconReceipt, IconUser} from "@tabler/icons-react";
import customStyle from "../../../styles/customStyle";
import {useRecoilState, useRecoilValue} from "recoil";
import {menuDrawerState} from "../../../states/menuDrawerState";
import {CheckLogin} from "../../../util/CheckLogin";
import {userState} from "../../../states/userState";
import {useFetch} from "../../../util/hook/useFetch";

function MobileMenuDrawer() {
    const {classes} = customStyle();
    const {pathname} = useLocation();
    const navigate = useNavigate();
    const {logoutFetch} = useFetch();
    const isLoggedIn = CheckLogin();
    const userStateValue = useRecoilValue(userState);
    const [opened, setOpened] = useRecoilState(menuDrawerState);

    const MENU_LIST = [
        {value: "홈", link: "/", icon: <IconHome/>},
        {value: "주최하기", link: "/write", icon: <IconPlus/>},
        {value: "마이페이지", link: "/users/profile", icon: <IconUser/>},
        {value: "주최 내역", link: "/users/events", icon: <IconReceipt/>},
        {value: "예약 내역", link: "/users/bookings", icon: <IconReceipt/>},
    ];

    const items = MENU_LIST.map(item => {
        if (((item.value === "주최 내역" || item.value === "주최하기") && (!userStateValue.isHost || !isLoggedIn)) ||
            (item.value === "예약 내역" && (userStateValue.isHost || !isLoggedIn))) {
            return;
        }
        return (
            <UnstyledButton key={item.value}
                            component={Link}
                            to={item.link}
                            style={{padding: "0.2rem 0"}}>
                <Group>
                    {item.icon}
                    <Text>{item.value}</Text>
                </Group>
            </UnstyledButton>
        )
    });

    const handleMenuDrawer = () => {
        setOpened((prev) => !prev);
    }

    return (
        <>
            <Drawer.Root opened={opened}
                         onClose={handleMenuDrawer}
                         position={"right"}
                         size={"70%"}
                         transitionProps={{duration: 400}}
                         zIndex={1001}
            >
                <Drawer.Overlay opacity={0.5} blur={2}/>
                <Drawer.Content>
                    <Container>
                        <Drawer.Header>
                        </Drawer.Header>
                        <Drawer.Body>
                            <Stack>
                                <Flex align={"center"} gap={"1rem"}>
                                    <Avatar size={"3.5rem"}
                                            radius={"4rem"}
                                            src={`${process.env["REACT_APP_NCLOUD_IMAGE_PATH"]}/${userStateValue.imagePath}`}/>
                                    <Text style={{whiteSpace: "pre-wrap"}}>
                                        {isLoggedIn ?
                                            userStateValue.email :
                                            "로그인 후 \n이용 가능합니다"}
                                    </Text>
                                </Flex>
                                <Divider/>

                                {items}

                                <Button onClick={() => isLoggedIn ?
                                    logoutFetch() : navigate("/login", {state: pathname})}
                                        className={classes["btn-gray-outline"]}>
                                    {isLoggedIn ? "로그아웃" : "로그인"}
                                </Button>
                            </Stack>
                        </Drawer.Body>
                    </Container>
                </Drawer.Content>
            </Drawer.Root>
        </>
    );
}

export default MobileMenuDrawer;