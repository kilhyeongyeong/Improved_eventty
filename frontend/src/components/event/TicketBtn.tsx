import React from "react";
import {Badge, Group, Paper, Stack, Text} from "@mantine/core";
import customStyle from "../../styles/customStyle";
import {useNavigate} from "react-router-dom";
import {CheckLogin} from "../../util/CheckLogin";
import {useModal} from "../../util/hook/useModal";

interface ITicket {
    id: number;
    name: string;
    price: number;
    quantity: number;
    applied: number;
    isActive?: boolean;
}

function TicketBtn({id, name, price, quantity, applied, isActive}: ITicket) {
    const {classes} = customStyle();
    const navigate = useNavigate();
    const {loginAlertModal} = useModal();
    const isLoggedIn = CheckLogin();
    const state = isActive && (quantity - applied > 0);

    const handleOnClick = (id: number) => {
        if (isActive) {
            if (!isLoggedIn) {
                loginAlertModal();
            } else {
                navigate(`booking?item=${id}`);
            }
        }
    }

    return (
        <>
            <Paper p={"md"} withBorder
                   onClick={() => handleOnClick(id)}
                   className={`${classes["ticket-select"]} ${!isActive && "disabled"}`}>
                <Stack>
                    <Group position={"apart"}>
                        <Text fw={"1000"} fz={"xl"}>
                            {price > 0 ? `${price.toLocaleString("ko-kr")} 원` : "무료"}
                        </Text>
                        <Badge radius={"sm"} color={state ? "red" : "gray"} style={{padding: "0.7rem 0.5rem"}}>
                            {state ?
                                `${(quantity - applied).toLocaleString("ko-kr")}개 남음` :
                                "예약 종료"}
                        </Badge>
                    </Group>
                    <Text fz={"sm"}>{name}</Text>
                </Stack>
            </Paper>
        </>
    );
}

export default TicketBtn;