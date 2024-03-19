import React from "react";
import {useRecoilValue} from "recoil";
import {Button, Stack, TextInput} from "@mantine/core";
import customStyle from "../../styles/customStyle";
import {Controller, useForm} from "react-hook-form";
import {IFindEmail} from "../../types/IUser";
import PhoneNumberInput from "../../components/common/PhoneNumberInput";
import {useFetch} from "../../util/hook/useFetch";
import {loadingState} from "../../states/loadingState";

function FindEmail() {
    const {classes} = customStyle();
    const {findEmailFetch} = useFetch();
    const loading = useRecoilValue(loadingState);

    const nameRegEX = /^[가-힣]{2,}$/;
    const phoneRegEX = /^01([0|1|6|7|8|9])-([0-9]{4})-([0-9]{4})$/;
    const {register, handleSubmit, control, formState: {errors}} = useForm<IFindEmail>();

    const onSubmit = (data: IFindEmail) => {
        findEmailFetch(data);
    }

    return (
        <Stack spacing={"1.5rem"}>
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
                loading={loading}
                loaderPosition={"center"}
            >
                이메일 찾기
            </Button>
        </Stack>
    );
}

export default FindEmail;