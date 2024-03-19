import React, {useCallback} from "react";
import {Stack, Title} from "@mantine/core";
import TicketBtn from "../TicketBtn";
import {useNavigate} from "react-router-dom";
import {IEventTicketDetail} from "../../../types/IEvent";
import {CheckLogin} from "../../../util/CheckLogin";
import {useModal} from "../../../util/hook/useModal";

interface ITickets {
    tickets: IEventTicketDetail[];
    isActive: boolean;
}

function WebTicketInfo({tickets, isActive}: ITickets) {
    const navigate = useNavigate();
    const isLoggedIn = CheckLogin();
    const {loginAlertModal} = useModal();

    const onClickTicket = useCallback((id: number) => {
        if (isLoggedIn) {
            navigate(`/event/${id}/booking`);
        } else {
            loginAlertModal();
        }
    }, [isLoggedIn]);

    const items = tickets.map((item) => (
        <TicketBtn key={item.id}
                   id={item.id}
                   name={item.name}
                   price={item.price}
                   quantity={item.quantity}
                   applied={item.appliedTicketCount}
                   isActive={isActive}/>
    ));

    return (
        <Stack>
            <Title order={4}>티켓 선택</Title>
            {items}
        </Stack>
    );
}

export default WebTicketInfo;