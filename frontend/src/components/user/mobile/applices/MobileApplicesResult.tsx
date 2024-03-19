import React, {useEffect, useState} from "react";
import {
    Anchor,Breadcrumbs,Button,
    Container,
    Drawer,
    Flex,
    Grid,
    Group,
    Radio,
    Stack,
    Text,
    TextInput,
} from "@mantine/core";
import {Controller, useForm} from "react-hook-form";
import EventDatePicker from "../../../write/EventDatePicker";
import {IconFilterSearch} from "@tabler/icons-react";
import WebApplicesTable from "../../web/applices/WebApplicesTable";
import customStyle from "../../../../styles/customStyle";
import {useLoaderData, useLocation, useNavigate, useParams} from "react-router-dom";
import {IEventApplices, IEventApplicesResult} from "../../../../types/IEvent";
import {getApplyUsers} from "../../../../service/event/fetchEvent";

type ObjType = {
    [index: string]: string
    all: string
    payment: string
    canceled: string
}
const state: ObjType = {
    all: "0",
    payment: "1",
    canceled: "2",
};

function MobileApplicesResult() {
    const {classes} = customStyle();
    const [mounted, setMounted] = useState(false);
    const [DATA, setDATA] = useState(useLoaderData() as IEventApplicesResult[]);
    const {eventId} = useParams();
    const {pathname} = useLocation();
    const navigate = useNavigate();
    const curPath = pathname.split("/").at(-2);
    const [drawerOpen, setDrawerOpen] = useState(false);

    const {
        register,
        handleSubmit,
        reset,
        control
    } = useForm<IEventApplices>({
        defaultValues: {
            eventId: eventId!.toString(),
            state: state[curPath!],
            order: "0",
            applyId: "",
            phone: "",
            name: "",
            priceMin: "",
            priceMax: "",
            dateMin: undefined,
            dateMax: undefined,
        }
    });

    const onSubmit = (data: IEventApplices) => {
        setDrawerOpen(prev => !prev);

        const query = Object.entries(data)
            .map(([key, value]) => {
                if (value === undefined || value === "" || value === null) return `${key}`;
                else if ((key === "dateMin" || key === "dateMax")) return `${key}=${value.toJSON().replace("Z", "")}`
                return `${key}=${value}`;
            })
            .join("&")

        getApplyUsers(query)
            .then((res) => setDATA(res));
    }

    const handleDrawer = () => {
        setDrawerOpen(prev => !prev);
    }

    useEffect(() => {
        if (!mounted) {
            setMounted(true);
        }else{
            const changeState = state[curPath!];
            const request = {
                eventId: eventId!.toString(),
                state: changeState,
                order: "0",
            }
            getApplyUsers(new URLSearchParams(request).toString())
                .then(res => setDATA(res));
        }
    }, [pathname, mounted]);

    return (
        <Stack spacing={"2rem"}>
            <Drawer opened={drawerOpen}
                    onClose={handleDrawer}
                    withCloseButton={false}
                    position={"right"}
                    overlayProps={{opacity: 0.5, blur: 4}}
                    zIndex={1002}>
                <Container>
                    <Stack spacing={"1.5rem"} style={{paddingTop: "1.5rem"}}>
                        <Grid>
                            <Grid.Col span={4}>
                                <Text>예약 번호</Text>
                            </Grid.Col>
                            <Grid.Col span={"auto"}>
                                <TextInput {...register("applyId")}
                                           type={"number"}
                                           size={"xs"}
                                           className={classes["input"]}/>
                            </Grid.Col>
                        </Grid>
                        <Grid>
                            <Grid.Col span={4}>
                                <Text>이름</Text>
                            </Grid.Col>
                            <Grid.Col span={"auto"}>
                                <TextInput {...register("name")}
                                           size={"xs"}
                                           className={classes["input"]}/>
                            </Grid.Col>
                        </Grid>
                        <Grid>
                            <Grid.Col span={4}>
                                <Text>금액</Text>
                            </Grid.Col>
                            <Grid.Col span={"auto"}>
                                <Group noWrap>
                                    <TextInput {...register("priceMin")}
                                               type={"number"} size={"xs"} className={classes["input"]}/>
                                    <Text>~</Text>
                                    <TextInput {...register("priceMax")}
                                               type={"number"} size={"xs"} className={classes["input"]}/>
                                </Group>
                            </Grid.Col>
                        </Grid>
                        <Grid>
                            <Grid.Col span={4}>
                                <Text>일시</Text>
                            </Grid.Col>
                            <Grid.Col span={8}>
                                <Stack>
                                    <Controller control={control}
                                                name={"dateMin"}
                                                render={({field}) => (
                                                    <EventDatePicker {...field}
                                                                     valueFormat={"YY.MM.DD 부터"}/>
                                                )}/>
                                    <Controller control={control}
                                                name={"dateMax"}
                                                render={({field}) => (
                                                    <EventDatePicker {...field}
                                                                     valueFormat={"YY.MM.DD 까지"}/>
                                                )}/>
                                </Stack>
                            </Grid.Col>
                        </Grid>
                        <Grid>
                            <Grid.Col span={4}>
                                <Text>정렬</Text>
                            </Grid.Col>
                            <Grid.Col span={"auto"}>
                                <Radio.Group>
                                    <Group noWrap>
                                        <Radio {...register("order")}
                                               label={"최신 순"}
                                               value={"0"}
                                               className={classes["radio-primary"]}/>
                                        <Radio {...register("order")}
                                               label={"등록 순"}
                                               value={"1"}
                                               className={classes["radio-primary"]}/>
                                    </Group>
                                </Radio.Group>
                            </Grid.Col>
                        </Grid>
                        <Group noWrap>
                            <Button
                                fullWidth
                                onClick={() => reset()}
                                className={classes["btn-primary-outline"]}>초기화</Button>
                            <Button
                                fullWidth
                                onClick={handleSubmit(onSubmit)}
                                className={classes["btn-primary"]}>조회</Button>
                        </Group>
                    </Stack>
                </Container>
            </Drawer>

            <Flex justify={"space-between"}>
                <Breadcrumbs>
                    <Anchor underline={false}
                            color={"var(--primary)"}
                            onClick={() => navigate("/users/events")}>
                        주최 내역
                    </Anchor>
                    <Anchor underline={false} color={"gray"} style={{cursor: "default"}}>신청 내역</Anchor>
                </Breadcrumbs>

                <Button onClick={handleDrawer}
                        rightIcon={<IconFilterSearch/>}
                        className={classes["btn-primary-outline"]}>
                    필터
                </Button>
            </Flex>
            <WebApplicesTable data={DATA}/>
        </Stack>
    );
}

export default MobileApplicesResult;