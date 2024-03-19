import React from "react";
import {SimpleGrid, Text, UnstyledButton} from "@mantine/core";
import {Link} from "react-router-dom";
import {IconConfetti, IconHome, IconMenu2, IconSearch} from "@tabler/icons-react";
import customStyle from "../../../../styles/customStyle";
import {useSetRecoilState} from "recoil";
import {searchDrawerState} from "../../../../states/searchDrawerState";
import {menuDrawerState} from "../../../../states/menuDrawerState";

function MobileNavBar() {
    const {classes} = customStyle();
    const setSearchDrawerValue = useSetRecoilState(searchDrawerState);
    const setMenuDrawerValue = useSetRecoilState(menuDrawerState);

    const ICON_SIZE = "7vw"
    const MENU_LIST = [
        {value: "홈", link: "/", icon: <IconHome size={ICON_SIZE}/>},
        {value: "행사", link: "/events", icon: <IconConfetti size={ICON_SIZE}/>},
        {value: "검색", link: "", icon: <IconSearch size={ICON_SIZE}/>},
        {value: "메뉴", link: "", icon: <IconMenu2 size={ICON_SIZE}/>},
    ];

    const items = MENU_LIST.map((item) => (
        item.link !== "" ?
            <UnstyledButton key={item.value}
                            component={Link}
                            to={item.link}
                            className={classes["mobile-nav-link"]}>
                {item.icon}
                <Text fz={"0.8rem"}>{item.value}</Text>
            </UnstyledButton> :
            <UnstyledButton key={item.value}
                            className={classes["mobile-nav-link"]}
                            onClick={() => {
                                item.value === "메뉴" ?
                                    setMenuDrawerValue(true) :
                                    setSearchDrawerValue(true)
                            }}>
                {item.icon}
                <Text fz={"0.8rem"}>{item.value}</Text>
            </UnstyledButton>
    ));

    return (
        <SimpleGrid cols={4} style={{width: "100%", alignItems: "flex-end", padding: "0 3vw"}}>
            {items}
        </SimpleGrid>
    );
}

export default MobileNavBar;