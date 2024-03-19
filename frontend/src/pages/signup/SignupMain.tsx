import {Button, Stack} from "@mantine/core";
import { Link } from "react-router-dom";
import { useSetRecoilState } from 'recoil';
import { cardTitleState } from '../../states/cardTitleState';
import { useEffect } from 'react';
import customStyle from "../../styles/customStyle";

function SignupMain() {
    const { classes } = customStyle();

    const setCardTitleState = useSetRecoilState(cardTitleState);

    useEffect(() => {
        setCardTitleState("회원가입");
    }, []);

    return (
        <Stack style={{paddingTop: "2rem"}}>
            <Button style={{height:"2.6rem"}} className={classes["btn-primary"]} component={Link} to={"user"}>
                개인 회원가입
            </Button>
            <Button style={{height:"2.6rem"}} className={classes["btn-gray-outline"]} component={Link} to={"host"}>
                <span style={{color:"var(--primary)"}}>주최자&nbsp;</span> 회원가입
            </Button>
        </Stack>
    )
}

export default SignupMain;