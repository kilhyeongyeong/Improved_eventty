import React, {useEffect} from "react";
import {useSetRecoilState} from "recoil";
import {cardTitleState} from "../../states/cardTitleState";
import {Button, Stack, TextInput} from "@mantine/core";
import {Controller, useForm} from "react-hook-form";
import PhoneNumberInput from "../../components/common/PhoneNumberInput";
import {IFindPassword} from "../../types/IUser";
import {useFetch} from "../../util/hook/useFetch";
import customStyle from "../../styles/customStyle";

function FindPassword() {
    const {classes} = customStyle();
    const setCardTitleState = useSetRecoilState(cardTitleState);
    const {findPasswordFetch} = useFetch();

    const emailRegEx = /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/;
    const nameRegEX = /^[가-힣]{2,}$/;
    const phoneRegEX = /^01([0|1|6|7|8|9])-([0-9]{4})-([0-9]{4})$/;
    const {register, handleSubmit, control, formState: {errors}} = useForm<IFindPassword>();

    const onSubmit = (data: IFindPassword) => {
        findPasswordFetch(data);
    }

    useEffect(() => {
        setCardTitleState("비밀번호 찾기");
    }, []);

    return (
        <Stack spacing={"1.5rem"}>
            <TextInput {...register("email", {
                required: "이메일을 입력해주세요",
                pattern: {value: emailRegEx, message: "이메일 형식이 올바르지 않습니다"}
            })}
                       placeholder="이메일"
                       className={classes["input"]}
                       error={errors.email?.message}
            />
            <TextInput {...register("name", {
                required: "이름을 입력해주세요",
                pattern: {value: nameRegEX, message: "이름이 올바르지 않습니다"}
            })}
                       placeholder="이름"
                       className={classes["input"]}
                       error={errors.name?.message}
            />
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
                                              asterisk={false}
                                              label={false}/>
                        )}/>
            <Button
                style={{height: "2.6rem"}}
                className={classes["btn-primary"]}
                onClick={handleSubmit(onSubmit)}
            >
                비밀번호 찾기
            </Button>
        </Stack>
    );
}

export default FindPassword;