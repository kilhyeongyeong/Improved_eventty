import React from "react";
import {Divider, Title} from "@mantine/core";
import {Outlet, useParams, useSearchParams} from "react-router-dom";
import SearchResult from "../SearchResult";
import {CATEGORY_LIST} from "../../../util/const/categoryList";

type TParams = {
    category: string;
}

function WebEvents() {
    const {category} = useParams<keyof TParams>() as TParams;
    const [searchParams, setSearchParams] = useSearchParams();

    return (
        <>
            {category &&
                <>
                    <Title>{CATEGORY_LIST[category]}</Title>
                    <Divider my={"1rem"}/>
                </>}
            {searchParams.size > 0 && <SearchResult/>}
            <Outlet/>
        </>
    );
}

export default WebEvents;