import React, {useEffect, useState} from "react";
import {Avatar, Button, Divider, Group, Menu, Text} from "@mantine/core";
import {useRecoilValue} from "recoil";
import {userState} from "../../../../states/userState";
import {IconReceipt, IconUser} from "@tabler/icons-react";
import {Link} from "react-router-dom";
import customStyles from "../../../../styles/customStyle";
import {useFetch} from "../../../../util/hook/useFetch";

function WebUserInfoBtn() {
    const {classes} = customStyles();
    const userStateValue = useRecoilValue(userState);
    const [profileImg, setProfileImg] = useState(`${process.env["REACT_APP_NCLOUD_IMAGE_PATH"]}/${userStateValue.imagePath}`);
    const {logoutFetch} = useFetch();

    useEffect(() => {
        setProfileImg(userStateValue.imagePath ? `${process.env["REACT_APP_NCLOUD_IMAGE_PATH"]}/${userStateValue.imagePath}` : "");
    }, [userStateValue.imagePath]);

    return (
        <>
            {userStateValue.isHost &&
                <Button component={Link}
                        to={"/write"}
                        className={classes["btn-primary-outline"]}
                        style={{padding: "0 2rem", marginRight: "1rem"}}>
                    주최하기
                </Button>
            }
            <Menu width={"12rem"} shadow={"sm"} position={"top-end"}>
                <Menu.Target>
                    <Avatar src={profileImg} radius={"xl"} style={{cursor: "pointer"}}/>
                </Menu.Target>
                <Menu.Dropdown>
                    <Menu.Item style={{pointerEvents: "none"}}>
                        <Group noWrap>
                            <Avatar src={profileImg} radius={"xl"}/>
                            <Text style={{wordBreak: "break-all"}}>{userStateValue.email}</Text>
                        </Group>
                    </Menu.Item>
                    <Divider/>

                    <Menu.Item icon={<IconUser/>} component={Link} to={"/users/profile"}>마이페이지</Menu.Item>
                    <Menu.Item icon={<IconReceipt/>}
                               component={Link}
                               to={userStateValue.isHost ? "/users/events" : "/users/bookings"}>
                        {userStateValue.isHost ? "주최 내역" : "예약 내역"}
                    </Menu.Item>
                    <Menu.Divider/>

                    <Menu.Item onClick={() => logoutFetch()}>로그아웃</Menu.Item>
                </Menu.Dropdown>
            </Menu>
        </>
    );
}

export default WebUserInfoBtn;