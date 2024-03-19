import React from "react";
import {Button, Stack, Title} from "@mantine/core";
import {useLocation, useNavigate} from "react-router-dom";
import customStyle from "../../styles/customStyle";

function FindResultEmail() {
    const {classes} = customStyle();
    const {state} = useLocation();
    const navigate = useNavigate();

    return (
        <Stack spacing={"2rem"}>
            <Stack align={"center"}>
                <Title order={1}>사용자의 이메일은</Title>
                <Title order={1}>
                    <span style={{color: "var(--primary)"}}>{state[0].email}</span>입니다
                </Title>
            </Stack>
            <Button onClick={() => navigate("/login")}
                    className={classes["btn-primary-outline"]}>돌아가기</Button>
        </Stack>
    );
}

export default FindResultEmail;