import React from "react";
import {Divider, Text, Title} from "@mantine/core";
import {useRouteLoaderData, useSearchParams} from "react-router-dom";
import {IEvent} from "../../types/IEvent";

function SearchResult() {
    const [searchParams, setSearchParams] = useSearchParams();
    const DATA = useRouteLoaderData("search") as IEvent[];

    return (
        <>
            <Title color={"#cdcdcd"}>
                <Text color={"black"} span inherit>{searchParams.get("keyword")}</Text> 검색 결과
            </Title>
            <Text><Text fw={"bold"} fz={"1.5rem"} color={"var(--primary)"} span inherit>{DATA ? DATA.length : "0"}</Text>개의 행사가 있습니다</Text>
            <Divider my={"1rem"}/>
        </>
    );
}

export default SearchResult;