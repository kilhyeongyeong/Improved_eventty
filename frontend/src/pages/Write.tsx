import React, {useEffect, useState} from "react";
import {
    Button,
    Container, FileButton,
    Group, Image,
    Paper,
    Select, SimpleGrid,
    Stack, Text,
    TextInput,
    Title,
    UnstyledButton
} from "@mantine/core";
import customStyle from "../styles/customStyle";
import {Controller, FormProvider, useFieldArray, useForm} from "react-hook-form";
import {IEventTicket, IEventWrite} from "../types/IEvent";
import WriteHeader from "../components/display/WriteHeader";
import {IconPlus, IconX} from "@tabler/icons-react";
import EventDatePicker from "../components/write/EventDatePicker";
import TicketSubmitModal from "../components/write/TicketSubmitModal";
import TicketEditModal from "../components/write/TicketEditModal";
import {CheckXsSize} from "../util/CheckMediaQuery";
import QuillEditor from "../components/common/QuillEditor";
import {useFetch} from "../util/hook/useFetch";
import {useSetRecoilState} from "recoil";
import {loadingState} from "../states/loadingState";

function Write() {
    const {classes} = customStyle();

    const {createEventFetch} = useFetch();
    const isMobile = CheckXsSize();
    const setLoading = useSetRecoilState(loadingState);
    const [ticketModalOpened, setTicketModalOpened] = useState(false);
    const [ticketEditModalOpened, setTicketEditModalOpened] = useState(false);

    const {
        register,
        handleSubmit,
        control,
        watch,
        getValues,
        setError,
        clearErrors,
        formState: {errors}
    } = useForm<IEventWrite>();
    const {fields, append, remove, update} = useFieldArray({
        control,
        name: "tickets",
        rules: {
            required: "티켓을 설정해주세요",
        }
    });

    const ticketMethods = useForm<IEventTicket>();
    const ticketEditMethods = useForm<IEventTicket>();

    const currentDate = new Date();
    const CATEGORY_LIST = [
        {label: "콘서트", value: "concert"},
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
    const TICKET_LIMIT = 3;
    const [ticketEdit, setTicketEdit] = useState<IEventTicket | null>(null);
    const [ticketIdx, setTicketIdx] = useState(0);
    const disabledTitle = fields.map((ticket) => ticket.name);

    const [imgFile, setImgFile] = useState<File | null>(null);
    const [imgPreview, setImgPreview] = useState("");

    const onSubmit = (data: IEventWrite) => {
        if (imgFile === null) {
            setError("image", {types: {validate: "이미지를 업로드해주세요"}})
            return;
        }

        data.eventStartAt?.setDate(data.eventStartAt?.getDate() + 1);
        data.eventEndAt?.setDate(data.eventEndAt?.getDate() + 1);
        data.applyStartAt?.setDate(data.applyStartAt?.getDate() + 1);
        data.applyEndAt?.setDate(data.applyEndAt?.getDate() + 1);

        const formData = new FormData();
        formData.append("image", imgFile!);
        formData.append("eventInfo", new Blob([JSON.stringify(data)], {type: "application/json"}));

        setLoading(true);
        createEventFetch(formData);
    }

    const onTicketCreate = (data: IEventTicket) => {
        append(data);
    }

    const onTicketEdit = (data: IEventTicket) => {
        update(ticketIdx, data);
    }

    const handleTicketModalOpened = () => {
        setTicketModalOpened(prev => !prev);
    }

    const handleTicketEditModalOpened = (data: IEventTicket, idx: number) => {
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
                    <IconX size={"1rem"}
                           style={{top: "1rem", right: "1rem", position: "absolute"}}
                           onClick={(event) => {
                               event.stopPropagation();
                               remove(idx);
                           }}/>
                    <Stack spacing={"0.5rem"}>
                        <Title order={4}>{item.name}</Title>
                        <Text>{item.quantity}명</Text>
                        <Text>{item.price === 0 ? "무료" : `${item.price.toLocaleString("ko-KR")}원`}</Text>
                    </Stack>
                </Paper>
            </UnstyledButton>
        )
    });

    useEffect(() => {
        if (imgFile !== null) {
            setImgPreview(URL.createObjectURL(imgFile));
            clearErrors("image");
        }

    }, [imgFile]);

    return (
        <>
            {/* 티켓 등록 Modal */}
            <FormProvider {...ticketMethods}>
                <form onSubmit={ticketMethods.handleSubmit(onTicketCreate)}>
                    <TicketSubmitModal open={ticketModalOpened}
                                       title={disabledTitle}/>
                </form>
            </FormProvider>

            {/* 티켓 수정 Modal */}
            {ticketEdit !== null &&
                <FormProvider {...ticketEditMethods}>
                    <form onSubmit={ticketEditMethods.handleSubmit(onTicketEdit)}>
                        <TicketEditModal open={ticketEditModalOpened}
                                         title={disabledTitle}
                                         data={ticketEdit}
                                         update={false}/>
                    </form>
                </FormProvider>
            }

            <form>
                <WriteHeader onSubmit={handleSubmit(onSubmit)}/>
                <Container>
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
                                <UnstyledButton onClick={handleTicketModalOpened}
                                                hidden={ticketItems.length === TICKET_LIMIT}>
                                    <Paper style={{
                                        border: "1px dashed #cdcdcd",
                                        color: "#cdcdcd",
                                        height: "120px",
                                        display: "flex",
                                        justifyContent: "center",
                                        alignItems: "center"
                                    }}>
                                        <IconPlus/>
                                    </Paper>
                                </UnstyledButton>
                            </SimpleGrid>
                            <Text fz={"13px"} color={"#f44336"}>
                                {errors.tickets?.root?.message}
                                {errors.tickets?.types?.validate}
                            </Text>
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
                                <div>
                                    <FileButton onChange={setImgFile} accept={"image/png, image/jpeg"}>
                                        {(props) =>
                                            <Button {...props} className={classes["btn-primary"]}>
                                                이미지 선택
                                            </Button>}
                                    </FileButton>
                                </div>
                            </Group>
                            <Text fz={"13px"} color={"red"}>{errors.image?.types?.validate}</Text>
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
            </form>
        </>
    );
}

export default Write;