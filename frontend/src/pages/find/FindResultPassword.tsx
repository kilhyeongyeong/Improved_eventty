import React from "react";
import {Button, Stack, TextInput} from "@mantine/core";
import customStyle from "../../styles/customStyle";
import {useForm} from "react-hook-form";
import {IFindCallbackPassword} from "../../types/IUser";
import {useFetch} from "../../util/hook/useFetch";

function FindResultPassword() {
    const {classes} = customStyle();
    const {findPasswordCallbackFetch} = useFetch();

    const {
        register,
        handleSubmit,
        getValues,
        formState: {errors}
    } = useForm<IFindCallbackPassword>();

    const onSubmit = (data: IFindCallbackPassword) => {
        delete data.passwordConfirm;
        findPasswordCallbackFetch(data);
    }

    return (
        <Stack spacing={"1.5rem"}>
            <TextInput
                {...register("password", {
                    required: "비밀번호를 입력해주세요",
                    minLength: {
                        value: 8,
                        message: "최소 8자 이상 비밀번호를 입력해주세요"
                    },
                    maxLength: {
                        value: 16,
                        message: "16자 이하 비밀번호만 사용 가능합니다"
                    }
                })}
                type="password"
                placeholder="비밀번호"
                withAsterisk
                error={errors.password && errors.password?.message}
                className={`${classes["input"]} ${errors.password && "error"}`}/>
            <TextInput {...register("passwordConfirm", {
                required: "비밀번호를 다시 입력해주세요",
                validate: {
                    check: (value) => {
                        if (getValues("password") !== value) {
                            return "비밀번호가 일치하지 않습니다"
                        }
                    }
                }
            })}
                       type="password"
                       placeholder="비밀번호 확인"
                       error={errors.passwordConfirm && errors.passwordConfirm?.message}
                       className={`${classes["input"]} ${errors.passwordConfirm && "error"}`}/>
            <Button
                style={{height: "2.6rem"}}
                className={classes["btn-primary"]}
                onClick={handleSubmit(onSubmit)}
            >
                비밀번호 변경
            </Button>
        </Stack>
    );
}

export default FindResultPassword;