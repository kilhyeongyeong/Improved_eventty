import React, {useEffect, useRef, useState} from "react";
import {
    Avatar,
    Button,
    Divider,
    FileButton,
    Flex,
    Group,
    Stack,
    TextInput,
    Title
} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import BirthdayPicker from "../../common/BirthdayPicker";
import PhoneNumberInput from "../../common/PhoneNumberInput";
import {useLoaderData} from "react-router-dom";
import {IUpdateUser, IUser} from "../../../types/IUser";
import {useFetch} from "../../../util/hook/useFetch";
import {useModal} from "../../../util/hook/useModal";
import {Controller, useForm} from "react-hook-form";

function WebProfile() {
    const {classes} = customStyle();
    const DATA = useLoaderData() as IUser;
    const curEmail = sessionStorage.getItem("EMAIL")!;
    const nameRegEX = /^[가-힣A-Za-z]{2,}$/;
    const phoneRegEX = /^01([0|1|6|7|8|9])-([0-9]{4})-([0-9]{4})$/;
    const [imgPreview, setImgPreview] = useState(DATA.imagePath && `${process.env["REACT_APP_NCLOUD_IMAGE_PATH"]}/${DATA.imagePath}`);
    const resetRef = useRef<() => void>(null);

    const {changeProfileFetch} = useFetch();
    const {changePWModal, accountDeleteModal} = useModal();

    const {register, handleSubmit, watch, getValues, setValue, control, formState: {errors}}
        = useForm<IUpdateUser>({
        defaultValues: {
            image: null,
            imageId: DATA.imageId,
            phone: DATA.phone,
            birth: new Date(DATA.birth!),
            address: DATA.address,
            name: DATA.name,
            isUpdate: false,
        }
    });

    const handleImageDelete = () => {
        setImgPreview("");
        setValue("image", null);
        setValue("isUpdate", true);
        resetRef.current?.();
    }

    const onSubmit = (data: IUpdateUser) => {
        data.birth?.setDate(data.birth?.getDate() + 1);
        if (data.image === null) {
            delete data.image;
            if (!data.isUpdate) {
                delete data.imageId;
            }
        }

        const formData = new FormData();
        for (const e in data) {
            if (e === "birth") {
                formData.append(e, data[e]!.toJSON().replaceAll("\"", ""));
            } else {
                formData.append(e, data[e]);
            }
        }

        changeProfileFetch(formData);
    }

    useEffect(() => {
        if (watch("image") !== null) {
            setImgPreview(URL.createObjectURL(getValues("image")!));
            setValue("isUpdate", true);
        }
    }, [watch("image")]);

    return (
        <>
            <Stack spacing={"3rem"}>
                <Stack>
                    <Title order={3}>프로필</Title>
                    <Divider/>
                    <Flex gap={"2rem"}>
                        <Stack align={"center"}>
                            <Avatar size={"8rem"}
                                    radius={"8rem"}
                                    src={imgPreview}
                                    onClick={handleImageDelete}
                                    style={{cursor: "pointer"}}
                            />
                            <FileButton onChange={(file) => setValue("image", file)}
                                        accept={"image/png, image/jpeg, image/webp"}
                                        resetRef={resetRef}>
                                {(props) => <Button {...props} className={classes["btn-primary"]}>이미지 변경</Button>}
                            </FileButton>
                        </Stack>

                        <Stack style={{width: "100%"}}>

                            <TextInput label={"이메일"}
                                       disabled
                                       defaultValue={curEmail}
                                       className={classes["input"]}/>

                            <TextInput {...register("name", {
                                required: "이름을 입력해주세요",
                                pattern: {value: nameRegEX, message: "이름이 올바르지 않습니다"},
                            })}
                                       label={"이름"}
                                       withAsterisk
                                       error={errors.name?.message}
                                       className={classes["input"]}/>

                            <Controller control={control}
                                        name={"phone"}
                                        rules={{
                                            required: "휴대폰 번호를 입력해주세요",
                                            pattern: {value: phoneRegEX, message: "휴대폰 번호가 올바르지 않습니다"},
                                        }}
                                        render={({field: {ref, ...rest}}) => (
                                            <PhoneNumberInput {...rest}
                                                              inputRef={ref}
                                                              error={errors.phone?.message}
                                                              asterisk={true}
                                                              label={true}/>
                                        )}/>

                            <Controller control={control}
                                        name={"birth"}
                                        render={({field: {ref, ...rest}}) => (
                                            <BirthdayPicker {...rest}
                                                            inputRef={ref}
                                                            label={"생년월일"}/>
                                        )}/>

                            <TextInput {...register("address")}
                                       label={"주소"}
                                       className={classes["input"]}/>

                            <Group position={"right"}>
                                <Button onClick={handleSubmit(onSubmit)}
                                        style={{width: "8rem"}}
                                        className={classes["btn-primary"]}>저장</Button>
                            </Group>
                        </Stack>
                    </Flex>
                </Stack>

                <Stack>
                    <Title order={3}>보안</Title>
                    <Divider/>
                    <Group position={"apart"}>
                        <Title order={5}>비밀번호</Title>
                        <Button onClick={() => changePWModal()}
                                style={{width: "8rem"}}
                                className={classes["btn-primary"]}>
                            비밀번호 변경
                        </Button>
                    </Group>
                </Stack>

                <Stack>
                    <Title order={3} color={"red"}>회원 탈퇴</Title>
                    <Divider/>
                    <Group position={"apart"}>
                        <Title order={5}>회원 탈퇴</Title>
                        <Button color={"red"}
                                style={{width: "8rem"}}
                                onClick={() => accountDeleteModal()}>
                            회원 탈퇴
                        </Button>
                    </Group>
                </Stack>
            </Stack>
        </>
    );
}

export default WebProfile;