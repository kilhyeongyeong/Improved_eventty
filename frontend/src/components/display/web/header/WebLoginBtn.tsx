import React from "react";
import {Button} from "@mantine/core";
import {useLocation, useNavigate} from "react-router-dom";
import customStyles from "../../../../styles/customStyle";

function WebLoginBtn() {
    const navigate = useNavigate();
    const {pathname} = useLocation();

    const {classes} = customStyles();

    return (
        <Button
            className={classes["btn-primary"]}
            onClick={() => navigate("/login", {state: pathname})}>
            로그인
        </Button>
    );
}

export default WebLoginBtn;