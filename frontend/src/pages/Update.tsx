import React, {useState} from "react";
import {
    Container,
    Group, Image,
    Paper,
    Select, SimpleGrid,
    Stack, Switch, Text,
    TextInput,
    Title,
    UnstyledButton
} from "@mantine/core";
import customStyle from "../styles/customStyle";
import {Controller, FormProvider, useFieldArray, useForm} from "react-hook-form";
import {IEventDetail, IEventTicketUpdate, IEventUpdate} from "../types/IEvent";
import WriteHeader from "../components/display/WriteHeader";
import EventDatePicker from "../components/write/EventDatePicker";
import TicketEditModal from "../components/write/TicketEditModal";
import {CheckXsSize} from "../util/CheckMediaQuery";
import QuillEditor from "../components/common/QuillEditor";
import {useFetch} from "../util/hook/useFetch";
import {useSetRecoilState} from "recoil";
import {loadingState} from "../states/loadingState";
import {useLoaderData} from "react-router-dom";
import {IconCheck, IconX} from "@tabler/icons-react";

function Update() {
    const {classes} = customStyle();
    const DATA = useLoaderData() as IEventDetail;
    const DATA_TICKET: IEventTicketUpdate[] = DATA.tickets.map(item => {
        return {
            id: item.id,
            name: item.name,
            price: item.price,
            quantity: item.quantity,
        };
    });

    const {updateEventFetch} = useFetch();
    const isMobile = CheckXsSize();
    const setLoading = useSetRecoilState(loadingState);
    const [ticketEditModalOpened, setTicketEditModalOpened] = useState(false);

    const {
        register,
        handleSubmit,
        control,
        watch,
        getValues,
        setValue,
        formState: {errors}
    } = useForm<IEventUpdate>({
        defaultValues: {
            title: DATA.title,
            eventStartAt: new Date(DATA.eventStartAt),
            eventEndAt: new Date(DATA.eventEndAt),
            location: DATA.location,
            category: DATA.category,
            content: DATA.content,
            isActive: DATA.isActive,
            applyStartAt: new Date(DATA.applyStartAt),
            applyEndAt: new Date(DATA.applyEndAt),
            ticketList: DATA_TICKET,
        }
    });
    const {fields, update} = useFieldArray({
        control,
        name: "ticketList",
        keyName: "key",
    });

    const ticketEditMethods = useForm<IEventTicketUpdate>();

    const currentDate = new Date();
    const CATEGORY_LIST = [
        {label: "콘서트", value: "conert"},
        {label: "클래식", value: "classical"},
        {label: "전시", value: "exhibition"},
        {label: "스포츠", value: "sports"},
        {label: "캠핑", value: "camping"},
        {label: "아동", value: "children"},
        {label: "영화", value: "movie"},
        {label: "IT", value: "it"},
        {label: "교양", value: "culture"},
        {label: "TOPIC", value: "topic"},
    ];
    const [ticketEdit, setTicketEdit] = useState<IEventTicketUpdate | null>(null);
    const [ticketIdx, setTicketIdx] = useState(0);
    const disabledTitle = fields.map((ticket) => ticket.name);

    const imgPreview = `${process.env["REACT_APP_NCLOUD_IMAGE_PATH"]}/${DATA.image}`;

    const onSubmit = (data: IEventUpdate) => {
        setLoading(true);
        updateEventFetch(data, DATA.id);
    }

    const onTicketEdit = (data: IEventTicketUpdate) => {
        update(ticketIdx, data);
    }

    const handleTicketEditModalOpened = (data: IEventTicketUpdate, idx: number) => {
        setTicketEdit(data);
        setTicketIdx(idx);
        setTicketEditModalOpened(prev => !prev);
    }

    const ticketItems = fields.map((item, idx) => {
        return (
            <UnstyledButton key={item.id} onClick={() => handleTicketEditModalOpened(item, idx)}>
                <Paper withBorder p={"1rem"}
                       style={{height: "120px", position: "relative"}}
                       className={classes["ticket-select"]}>
                    <Stack spacing={"0.5rem"}>
                        <Title order={4}>{item.name}</Title>
                        <Text>{item.quantity}명</Text>
                        <Text>{item.price === 0 ? "무료" : `${item.price.toLocaleString("ko-KR")}원`}</Text>
                    </Stack>
                </Paper>
            </UnstyledButton>
        )
    });

    return (
        <>
            {/* 티켓 수정 Modal */}
            {ticketEdit !== null &&
                <FormProvider {...ticketEditMethods}>
                    <form onSubmit={ticketEditMethods.handleSubmit(onTicketEdit)}>
                        <TicketEditModal open={ticketEditModalOpened}
                                         title={disabledTitle}
                                         data={ticketEdit}
                                         update={true}
                        />
                    </form>
                </FormProvider>
            }

            <WriteHeader onSubmit={handleSubmit(onSubmit)}/>
            <Container>
                <Stack>
                    <Title order={3}>행사 게시</Title>
                    <Switch checked={watch("isActive")}
                            onChange={(event) => setValue("isActive", event.currentTarget.checked)}
                            color={"teal"}
                            size={"lg"}
                            label={watch("isActive") ? "행사 게시" : "행사 게시 중지"}
                            thumbIcon={
                                watch("isActive") ?
                                    (<IconCheck size="0.8rem" color={"green"} stroke={3}/>)
                                    : (<IconX size="0.8rem" color={"red"} stroke={3}/>)
                            }/>
                </Stack>

                <Stack style={{margin: "5vh auto 15vh"}} spacing={"3rem"}>
                    <Stack>
                        <Title order={3}>제목</Title>
                        <TextInput {...register("title", {
                            required: "제목을 입력해주세요",
                        })}
                                   maxLength={50}
                                   error={errors.title?.message}
                                   className={`${classes["input"]} ${errors.title && "error"}`}/>
                    </Stack>

                    <Stack align={"flex-start"}>
                        <Title order={3}>카테고리</Title>
                        <Controller control={control}
                                    name={"category"}
                                    rules={{required: "카테고리를 선택해주세요"}}
                                    render={({field}) => (
                                        <Select
                                            {...field}
                                            data={CATEGORY_LIST}
                                            error={errors.category?.message}
                                            placeholder={"카테고리"}
                                            style={{width: isMobile ? "50vw" : "300px"}}
                                            className={classes["input-select"]}/>
                                    )}/>
                    </Stack>

                    <Stack>
                        <Title order={3}>티켓 정보</Title>
                        <SimpleGrid cols={4} breakpoints={[{maxWidth: "xs", cols: 2}]}>
                            {ticketItems}
                        </SimpleGrid>
                    </Stack>

                    <SimpleGrid cols={2} breakpoints={[{maxWidth: "xs", cols: 1, verticalSpacing: "2.5rem"}]}>
                        <Stack>
                            <Title order={3}>행사 일정</Title>
                            <Stack>
                                <Group>
                                    <Title order={4}>시작</Title>
                                    <Controller control={control}
                                                name={"eventStartAt"}
                                                rules={{required: "행사 시작 날짜를 지정해주세요",}}
                                                render={({field}) => (
                                                    <EventDatePicker
                                                        {...field}
                                                        minDate={currentDate}
                                                        error={errors.eventStartAt?.message}/>
                                                )}/>
                                </Group>

                                <Group>
                                    <Title order={4}>종료</Title>
                                    <Controller control={control}
                                                name={"eventEndAt"}
                                                rules={{
                                                    required: "행사 종료 날짜를 지정해주세요",
                                                    validate: (value) => new Date(getValues("eventStartAt")) <= value || "종료일은 시작일보다 앞설 수 없습니다",
                                                }}
                                                render={({field}) => (
                                                    <EventDatePicker
                                                        {...field}
                                                        minDate={watch("eventStartAt")}
                                                        error={errors.eventEndAt?.message}/>
                                                )}/>
                                </Group>
                            </Stack>
                        </Stack>
                        <Stack>
                            <Title order={3}>예약 일정</Title>
                            <Stack>
                                <Group>
                                    <Title order={4}>시작</Title>
                                    <Controller control={control}
                                                name={"applyStartAt"}
                                                rules={{required: "예약 시작 날짜를 지정해주세요",}}
                                                render={({field}) => (
                                                    <EventDatePicker
                                                        {...field}
                                                        minDate={currentDate}
                                                        error={errors.applyStartAt?.message}/>
                                                )}/>
                                </Group>

                                <Group>
                                    <Title order={4}>종료</Title>
                                    <Controller control={control}
                                                name={"applyEndAt"}
                                                rules={{
                                                    required: "예약 종료 날짜를 지정해주세요",
                                                    validate: (value) => new Date(getValues("applyStartAt")) <= value || "종료일은 시작일보다 앞설 수 없습니다",
                                                }}
                                                render={({field}) => (
                                                    <EventDatePicker
                                                        {...field}
                                                        minDate={watch("applyStartAt")}
                                                        error={errors.applyEndAt?.message}/>
                                                )}/>
                                </Group>
                            </Stack>
                        </Stack>
                    </SimpleGrid>

                    <Stack>
                        <Title order={3}>커버 이미지</Title>
                        <Group align={"flex-start"}>
                            <Image width={"280px"} height={"210px"} withPlaceholder
                                   src={imgPreview}/>
                            <Text>이미지는 수정하실 수 없습니다</Text>
                        </Group>
                    </Stack>

                    <Stack>
                        <Title order={3}>내용</Title>
                        <Controller control={control}
                                    name={"content"}
                                    rules={{required: "내용을 입력해주세요"}}
                                    defaultValue={""}
                                    render={({field: {ref, ...rest}}) => (
                                        <>
                                            <QuillEditor {...rest} inputRef={ref}/>
                                            <Text fz={"13px"} color={"red"}>{errors.content?.message}</Text>
                                        </>
                                    )}/>
                    </Stack>

                    <Stack>
                        <Title order={3}>장소</Title>
                        <TextInput {...register("location", {
                            required: "장소를 입력해주세요",
                        })}
                                   error={errors.location?.message}
                                   className={`${classes["input"]} ${errors.location && "error"}`}/>
                    </Stack>
                </Stack>
            </Container>
        </>
    );
}

export default Update;