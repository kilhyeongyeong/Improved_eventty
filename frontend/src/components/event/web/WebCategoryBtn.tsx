import React from "react";
import {Text, UnstyledButton} from "@mantine/core";
import {useNavigate} from "react-router-dom";
import customStyle from "../../../styles/customStyle";
import {
    IconBallBaseball, IconBook, IconCode,
    IconHandRock,
    IconHorseToy, IconMovie,
    IconPalette, IconPiano, IconPresentation,
    IconTent
} from "@tabler/icons-react";

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

function WebCategoryBtn() {
    const {classes} = customStyle();
    const navigate = useNavigate();

    const items = CATEGORY_LIST.map((item) => (

        <UnstyledButton key={item.category}
                        style={{
                            textAlign: "center",
                            padding: "0.5rem",
                        }}
                        sx={{
                            ":hover": {
                                color: "var(--primary)",
                            },
                        }}
                        onClick={() => navigate(`/events/category/${item.link}`)}
        >
            {item.icon}
            <Text fz={"xs"}>{item.category}</Text>
        </UnstyledButton>
    ));

    return (
        <>
            {items}
        </>
    );
}

export default WebCategoryBtn;