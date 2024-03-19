import React, {useEffect, useState} from 'react';
import {
    Button, Container,
    Divider,
    Flex,
    Grid,
    Group, NumberInput,
    Paper, SimpleGrid,
    Stack,
    Text, TextInput,
    Title, UnstyledButton
} from "@mantine/core";
import {useLoaderData, useRouteLoaderData, useSearchParams} from "react-router-dom";
import {IEventBooking, IEventDetail, IEventTicketDetail} from "../../../types/IEvent";
import customStyle from "../../../styles/customStyle";
import {IconCalendar, IconDeviceMobile, IconTicket, IconUsers} from "@tabler/icons-react";
import PhoneNumberInput from "../../common/PhoneNumberInput";
import {IUser} from "../../../types/IUser";
import {Controller, useForm} from "react-hook-form";
import {useFetch} from "../../../util/hook/useFetch";
import {useRecoilState} from "recoil";
import {loadingState} from "../../../states/loadingState";

function WebBookingTicket() {
    const {classes} = customStyle();
    const {applyEventFetch} = useFetch();
    const EVENT_DATA = useRouteLoaderData("event") as IEventDetail;
    const USER_DATA = useLoaderData() as IUser;
    const [searchParams, setSearchParams] = useSearchParams();
    const [curTicket, setCurTicket] = useState<IEventTicketDetail>(EVENT_DATA.tickets[0]);
    const [totalPrice, setTotalPrice] = useState(curTicket.price);
    const [loading, setLoading] = useRecoilState(loadingState);
    const eventStartAt = new Date(EVENT_DATA.eventStartAt);
    const eventEndAt = new Date(EVENT_DATA.eventEndAt);
    const phoneRegEX = /^01([0|1|6|7|8|9])-([0-9]{4})-([0-9]{4})$/;

    const {
        register,
        handleSubmit,
        watch,
        setValue,
        control,
        formState: {errors}
    } = useForm<IEventBooking>({
        defaultValues: {
            name: USER_DATA.name,
            phone: USER_DATA.phone,
            ticketId: curTicket.id,
            quantity: curTicket.quantity,
            applicantNum: 1,
            eventId: EVENT_DATA.id,
        }
    });

    const ticketItems = EVENT_DATA.tickets.map((item) => (
        <UnstyledButton key={item.id} onClick={() => handleTicketSelect(item)}>
            <Paper withBorder p={"1rem"}
                   style={{height: "100%", position: "relative", width: "100%"}}
                   className={`${classes["ticket-select"]} ${item.id === watch("ticketId") && "selected"}`}>
                <Stack>
                    <Title order={4} style={{wordBreak: "break-all"}}>
                        {item.price === 0 ? "무료" : `${item.price.toLocaleString("ko-KR")}원`}
                    </Title>
                    <Text>{item.name}</Text>
                </Stack>
            </Paper>
        </UnstyledButton>
    ));

    const onSubmit = (data: IEventBooking) => {
        setLoading(true);
        applyEventFetch(data);
    }

    const handleTicketSelect = (item: IEventTicketDetail) => {
        setValue("ticketId", item.id);
        setValue("quantity", item.quantity);
        setCurTicket(item);
    }

    useEffect(() => {
        const ticketId = Number(searchParams.get("item"));
        const item = EVENT_DATA.tickets.find(value => value.id === ticketId)!;

        if (typeof item !== "undefined") {
            setCurTicket(item);
            setTotalPrice(item.price);
            setValue("ticketId", item.id);
            setValue("quantity", item.quantity);
        }
    }, [searchParams]);

    useEffect(() => {
        setTotalPrice(curTicket.price * watch("applicantNum"));
    }, [watch("applicantNum"), curTicket]);

    return (
        <Container>
            <Grid>
                <Grid.Col span={8}>
                    <Stack style={{height: "100%"}}>
                        <Paper p={"1rem"}>
                            <Group noWrap>
                                <Title order={4}>{EVENT_DATA.title}</Title>
                            </Group>
                        </Paper>

                        <Paper p={"1rem"}>
                            <Stack spacing={"2rem"}>
                                <Group align={"flex-start"} noWrap>
                                    <IconTicket color={"var(--primary)"}/>
                                    <Stack style={{width: "100%"}} align={"flex-start"}>
                                        <Title order={4}>티켓</Title>
                                        <SimpleGrid cols={3} style={{width: "100%"}}>
                                            {ticketItems}
                                        </SimpleGrid>
                                    </Stack>
                                </Group>

                                <Group align={"flex-start"} noWrap>
                                    <IconUsers color={"var(--primary)"}/>
                                    <Stack style={{width: "100%"}} align={"flex-start"}>
                                        <Title order={4}>인원</Title>
                                        <Controller control={control}
                                                    name={"applicantNum"}
                                                    render={({field}) => (
                                                        <NumberInput
                                                            {...field}
                                                            defaultValue={1}
                                                            min={1}
                                                            max={999}
                                                            className={classes["input"]}
                                                            style={{width: "50%"}}/>
                                                    )}
                                        />
                                    </Stack>
                                </Group>
                            </Stack>
                        </Paper>

                        <Paper p={"1rem"} style={{height: "100%"}}>
                            <Group align={"flex-start"} noWrap>
                                <IconDeviceMobile color={"var(--primary)"}/>
                                <Stack style={{width: "100%"}} align={"flex-start"}>
                                    <Title order={4}>연락처</Title>
                                    <TextInput {...register("name", {
                                        required: "이름을 입력해주세요"
                                    })}
                                               description={"이름"}
                                               error={errors.name?.message}
                                               style={{width: "50%"}}
                                               className={classes["input"]}/>
                                    <Controller control={control}
                                                name={"phone"}
                                                rules={{
                                                    required: "휴대폰 번호를 입력해주세요",
                                                    pattern: {
                                                        value: phoneRegEX,
                                                        message: "휴대폰 번호가 올바르지 않습니다",
                                                    },
                                                }}
                                                render={({field: {ref, ...rest}}) => (
                                                    <PhoneNumberInput {...rest}
                                                                      inputRef={ref}
                                                                      description={true}
                                                                      error={errors.phone?.message}
                                                                      width={"50%"}/>
                                                )}/>
                                </Stack>
                            </Group>
                        </Paper>
                    </Stack>
                </Grid.Col>

                <Grid.Col span={4}>
                    <Paper p={"1rem"} style={{height: "100%"}}>
                        <Stack>
                            <Title order={4}>예약 정보</Title>
                            <Divider my={"0.5rem"}/>

                            <Group position={"apart"}>
                                <Text>{curTicket.name}</Text>
                                <Text>{curTicket.price.toLocaleString("ko-kr")} x {watch("applicantNum")}</Text>
                            </Group>
                            <Divider my={"0.5rem"}/>

                            <Stack spacing={"1.5rem"}>
                                <Stack spacing={"0.5rem"}>
                                    <Title order={6}>행사</Title>
                                    <Title order={5}>{EVENT_DATA.title}</Title>
                                </Stack>

                                <Stack spacing={"0.5rem"}>
                                    <Title order={6}>예약 일정</Title>
                                    <Group align={"flex-start"} noWrap>
                                        <IconCalendar color={"var(--primary)"}/>
                                        <Flex direction={"column"}>
                                            <Title order={5}>
                                                {`${eventStartAt.getFullYear()}년 
                                            ${eventStartAt.getMonth() + 1}월 
                                            ${eventStartAt.getDate()}일`}
                                            </Title>
                                            <Title order={5}>
                                                {`${eventStartAt.getHours()}:${eventStartAt.getMinutes() < 10 ? "0" : ""}${eventStartAt.getMinutes()} -
                                                ${eventEndAt.getHours()}:${eventEndAt.getMinutes() < 10 ? "0" : ""}${eventEndAt.getMinutes()}`}
                                            </Title>
                                        </Flex>
                                    </Group>
                                </Stack>
                            </Stack>
                            <Divider my={"0.5rem"}/>

                            <Group position={"apart"}>
                                <Title order={5}>총 금액</Title>
                                <Title order={4}>{totalPrice.toLocaleString("ko-kr")} 원</Title>
                            </Group>
                            <Button onClick={handleSubmit(onSubmit)}
                                    loading={loading}
                                    loaderPosition={"center"}
                                    className={classes["btn-primary"]}>결제하기</Button>
                        </Stack>
                    </Paper>
                </Grid.Col>
            </Grid>
        </Container>
    );
}

export default WebBookingTicket;