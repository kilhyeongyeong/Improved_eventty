import React, {useEffect} from "react";
import {useNavigate, useParams} from "react-router-dom";
import CardForm from "../components/signup/CardForm";
import {Stack, Tabs} from "@mantine/core";
import customStyle from "../styles/customStyle";
import FindEmail from "./find/FindEmail";
import FindPassword from "./find/FindPassword";
import {useSetRecoilState} from "recoil";
import {cardTitleState} from "../states/cardTitleState";

function Find() {
    const {classes} = customStyle();
    const navigate = useNavigate();
    const {params} = useParams();
    const setCardTitleState = useSetRecoilState(cardTitleState);

    const handleTabClick = (path: string) => {
        navigate(`/find/${path}`);
    };

    useEffect(() => {
        params === "email" ?
            setCardTitleState("이메일 찾기") :
            setCardTitleState("비밀번호 찾기");
    }, [params]);

    return (
        <CardForm>
            <Tabs defaultValue={params}
                  className={classes["tabs-primary"]}>
                <Stack spacing={"2rem"}>
                    <Tabs.List>
                        <Tabs.Tab value={"email"} onClick={() => handleTabClick("email")}>이메일 찾기</Tabs.Tab>
                        <Tabs.Tab value={"password"} onClick={() => handleTabClick("password")}>비밀번호 찾기</Tabs.Tab>
                    </Tabs.List>

                    <Tabs.Panel value={"email"}>
                        <FindEmail/>
                    </Tabs.Panel>
                    <Tabs.Panel value={"password"}>
                        <FindPassword/>
                    </Tabs.Panel>
                </Stack>
            </Tabs>
        </CardForm>
    );
}

export default Find;