import {Button, Container, Group, Header, Input, Paper, Transition, UnstyledButton} from "@mantine/core";
import {Link, useNavigate} from "react-router-dom";
import WebLoginBtn from "./WebLoginBtn";
import Logo from "../../../common/Logo";
import customStyle from "../../../../styles/customStyle";
import React, {useState} from "react";
import {useRecoilValue} from "recoil";
import {loginState} from "../../../../states/loginState";
import WebUserInfoBtn from "./WebUserInfoBtn";
import {IconSearch} from "@tabler/icons-react";
import {useModal} from "../../../../util/hook/useModal";
import WebCategoryBtn from "../../../event/web/WebCategoryBtn";
import {CheckSmSize} from "../../../../util/CheckMediaQuery";

const HEADER_HEIGHT = "65px";

function WebHeader() {
    const isLoggedIn = useRecoilValue(loginState);
    const isSmSize = CheckSmSize();
    const {classes} = customStyle();
    const {searchModal} = useModal();
    const [openMenu, setOpenMenu] = useState(false);
    const navigate = useNavigate();

    return (
        <>
            <Header height={HEADER_HEIGHT}
                    style={{boxShadow: openMenu ? "" : "0 2px 6px rgba(0, 0, 0, 0.1)", transition: "all 0.3s"}}
                    className={classes["header"]}>
                <Container
                    style={{
                        height: "100%",
                        display: "flex",
                        justifyContent: "space-between",
                        alignItems: "center",
                    }}>
                    <Group spacing={"lg"} align={"center"} style={{height: "100%"}}>
                        <Link to={"/"}>
                            <Logo fill={"var(--primary)"} height={"2rem"}/>
                        </Link>
                        <UnstyledButton
                            onClick={() => navigate("/events")}
                            onMouseEnter={() => setOpenMenu(true)}
                            onMouseLeave={() => setOpenMenu(false)}
                            style={{height: "100%"}}
                            className={classes["web-nav-link"]}>
                            행사
                        </UnstyledButton>
                    </Group>
                    <Group>
                        {isSmSize ?
                            <Button onClick={() => searchModal()}
                                    style={{padding: "0 0.4rem"}}
                                    className={classes["btn-gray-outline"]}>
                                <IconSearch/>
                            </Button> :
                            <Input component="button"
                                   rightSection={<IconSearch color={"#666666"}/>}
                                   onClick={() => searchModal()}
                                   style={{width: "13rem"}}
                                   sx={{
                                       ".mantine-Input-input": {
                                           borderColor: "#cdcdcd !important",
                                           color: "#cdcdcd !important",
                                           cursor: "pointer",
                                       },
                                   }}
                            >
                                검색
                            </Input>
                        }
                        {isLoggedIn ? <WebUserInfoBtn/> : <WebLoginBtn/>}
                    </Group>
                </Container>
            </Header>

            {/* 카테고리별 행사 조회 메뉴 */}
            <Transition mounted={openMenu} transition={"slide-down"} duration={300} timingFunction={"ease"}>
                {(styles) => (
                    <Paper
                        onMouseEnter={() => setOpenMenu(true)}
                        onMouseLeave={() => setOpenMenu(false)}
                        style={{
                            ...styles,
                            width: "100%",
                            height: "auto",
                            zIndex: 999,
                            position: "absolute",
                            top: 0,
                            paddingBottom: "40px",
                            boxShadow: "0 2px 6px rgba(0, 0, 0, 0.1)",
                        }}>
                        <Container style={{marginTop: (parseInt(HEADER_HEIGHT) + 20) + "px"}}>
                            <Group position={"apart"}>
                                <WebCategoryBtn/>
                            </Group>
                        </Container>
                    </Paper>
                )}
            </Transition>
        </>
    )
}

export default WebHeader;