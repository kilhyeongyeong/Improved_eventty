import React from "react";
import {Text, UnstyledButton} from "@mantine/core";
import {useNavigate} from "react-router-dom";
import {
    IconBallBaseball, IconBook, IconCode,
    IconHandRock,
    IconHorseToy, IconMovie,
    IconPalette, IconPiano, IconPresentation,
    IconTent
} from "@tabler/icons-react";
import customStyle from "../../../styles/customStyle";
import {useSetRecoilState} from "recoil";
import {searchDrawerState} from "../../../states/searchDrawerState";

const ICON_SIZE = "40px"
const CATEGORY_LIST = [
    {category: "콘서트", link: "concert", icon: <IconHandRock size={ICON_SIZE}/>},
    {category: "클래식", link: "classical", icon: <IconPiano size={ICON_SIZE}/>},
    {category: "전시", link: "exhibition", icon: <IconPalette size={ICON_SIZE}/>},
    {category: "스포츠", link: "sports", icon: <IconBallBaseball size={ICON_SIZE}/>},
    {category: "캠핑", link: "camping", icon: <IconTent size={ICON_SIZE}/>},
    {category: "아동", link: "children", icon: <IconHorseToy size={ICON_SIZE}/>},
    {category: "영화", link: "movie", icon: <IconMovie size={ICON_SIZE}/>},
    {category: "IT", link: "it", icon: <IconCode size={ICON_SIZE}/>},
    {category: "교양", link: "culture", icon: <IconBook size={ICON_SIZE}/>},
    {category: "TOPIC", link: "topic", icon: <IconPresentation size={ICON_SIZE}/>},
]

function MobileCategoryBtn() {
    const {classes} = customStyle();
    const navigate = useNavigate();
    const setSearchDrawer = useSetRecoilState(searchDrawerState);

    const handleOnClick = (link: string) => {
        setSearchDrawer(false);
        navigate(`/events/category/${link}`);
    }

    const items = CATEGORY_LIST.map((item) => (

        <UnstyledButton key={item.category}
                        className={classes["mobile-nav-link"]}
                        style={{textAlign: "center"}}
                        onClick={() => handleOnClick(item.link)}>
            {item.icon}
            <Text fz={"xs"}>{item.category}</Text>
        </UnstyledButton>
    ));

    return (
        <>{items}</>
    );
}

export default MobileCategoryBtn;