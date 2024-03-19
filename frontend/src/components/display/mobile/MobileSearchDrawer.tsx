import React, {useEffect, useState} from "react";
import {Drawer, Flex, Stack, Title} from "@mantine/core";
import {useRecoilState} from "recoil";
import {searchDrawerState} from "../../../states/searchDrawerState";
import {IconChevronLeft} from "@tabler/icons-react";
import SearchBox from "../../common/SearchBox";
import MobileCategoryBtn from "../../event/mobile/MobileCategoryBtn";
import customStyle from "../../../styles/customStyle";
import {SearchRecentHistory} from "../../../util/SearchRecentHistory";
import SearchKeywordsItem from "../SearchKeywordsItem";

function MobileSearchDrawer() {
    const {classes} = customStyle();
    const [opened, setOpened] = useRecoilState(searchDrawerState);
    const {keywords, handleAddKeyword, handleDeleteKeyword} = SearchRecentHistory();
    const [items, setItems] = useState<React.ReactNode[] | null>(null);

    const handleOpened = () => {
        setOpened(prev => !prev);
    }

    useEffect(() => {
        const mappedItems = keywords.map((item: string, idx: number) => (
            <SearchKeywordsItem key={idx} item={item} onClick={handleAddKeyword} onDelete={handleDeleteKeyword}/>
        ));

        setItems(mappedItems);
    }, [keywords]);

    return (
        <Drawer.Root opened={opened}
                     onClose={handleOpened}
                     position={"top"}
                     size={"100%"}
                     transitionProps={{duration: 400}}
                     zIndex={1001}
        >
            <Drawer.Overlay/>
            <Drawer.Content>
                <Drawer.Header>
                    <IconChevronLeft size={"3vh"}
                                     onClick={handleOpened}
                                     style={{paddingRight: "2vh"}}
                    />
                    <SearchBox onAddKeyword={handleAddKeyword}/>
                </Drawer.Header>
                <Drawer.Body>
                    <Stack spacing={"2rem"} style={{marginTop: "1rem"}}>
                        <Stack spacing={"2rem"}>
                            <Title order={4}>최근 검색어</Title>
                            {items}
                        </Stack>

                        <Title order={4}>카테고리</Title>
                        <Flex gap={"7vw"} className={classes["category-scroll"]}>
                            <MobileCategoryBtn/>
                        </Flex>
                    </Stack>
                </Drawer.Body>
            </Drawer.Content>
        </Drawer.Root>
    )
        ;
}

export default MobileSearchDrawer;