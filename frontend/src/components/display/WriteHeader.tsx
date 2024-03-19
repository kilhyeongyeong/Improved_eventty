import React from "react";
import {Box, Button, Container, Group, UnstyledButton} from "@mantine/core";
import {IconArrowLeft} from "@tabler/icons-react";
import customStyle from "../../styles/customStyle";
import {useLocation, useNavigate} from "react-router-dom";

interface IWriteSubmit {
    onSubmit: () => void;
}

function WriteHeader({onSubmit}: IWriteSubmit) {
    const {classes} = customStyle();
    const {state} = useLocation();
    const navigate = useNavigate();

    return (
        <Box style={{
            background: "white",
            position: "sticky",
            top: 0,
            width: "100%",
            height: "4rem",
            zIndex: 99
        }}>
            <Container style={{height: "100%"}}>
                <Group align={"center"} style={{height: "100%"}} position={"apart"}>
                    <UnstyledButton onClick={() => state !== null ? navigate(state) : navigate("/")}>
                        <IconArrowLeft color={"#666666"}/>
                    </UnstyledButton>

                    <Group>
                        {/* 임시 저장 기능 구현 X */}
                        {/*<Button className={classes["btn-primary-outline"]}>임시저장</Button>*/}
                        <Button onClick={onSubmit} className={classes["btn-primary"]}>게시하기</Button>
                    </Group>
                </Group>
            </Container>
        </Box>
    );
}

export default WriteHeader;