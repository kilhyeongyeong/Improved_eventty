import {Button, Checkbox, Grid, Stack, TextInput} from "@mantine/core";
import {useNavigate} from "react-router-dom";
import {useRecoilState} from "recoil";
import {Controller, useForm} from "react-hook-form";
import customStyles from "../../styles/customStyle";
import {useMemo, useState} from "react";
import {ISignup} from "../../types/IUser";
import "dayjs/locale/ko";
import {postSignupEmailValid, postSignupHost, postSignupUser} from "../../service/user/fetchUser";
import BirthdayPicker from "../common/BirthdayPicker";
import {useModal} from "../../util/hook/useModal";
import {loadingState} from "../../states/loadingState";
import {MessageAlert} from "../../util/MessageAlert";
import PhoneNumberInput from "../common/PhoneNumberInput";

function SignupForm({isHost}: { isHost: boolean }) {
    const {classes} = customStyles();
    const navigate = useNavigate();
    const {messageModal} = useModal();
    const [loadingStateValue, setLoadingStateValue] = useRecoilState(loadingState);

    const emailRegEx = /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/;
    const nameRegEX = /^[가-힣A-Za-z]{2,}$/;
    const phoneRegEX = /^01([0|1|6|7|8|9])-([0-9]{4})-([0-9]{4})$/;
    const {
        register, handleSubmit, getValues, setValue, control, watch, setFocus, formState: {errors}
    } = useForm<ISignup>({mode: "onChange"});

    const emailInputValue = watch("email");
    const [isEmailValid, setIsEmailValid] = useState(false);

    const [termOfServiceCheck, setTermOfServiceCheck] = useState(false);

    const onSubmit = (data: ISignup) => {
        delete data.passwordConfirm;
        data.birth?.setDate(data.birth?.getDate()+1);

        if (!isEmailValid) {
            messageModal("이메일 중복 확인 해주세요");
            setFocus("email");
            return;
        }

        setLoadingStateValue(true);
        if (isHost) {
            postSignupHost(data)
                .then(res => {
                    if (res === 201) {
                        MessageAlert("success", "회원 가입 완료", "로그인 해주세요");
                        navigate("/");
                    } else {
                        MessageAlert("error", "회원 가입 실패", "다시 시도해주세요");
                    }
                })
                .finally(() => setLoadingStateValue(false));
        } else {
            postSignupUser(data)
                .then(res => {
                    if (res === 201) {
                        MessageAlert("success", "회원 가입 완료", "로그인 해주세요");
                        navigate("/");
                    } else {
                        MessageAlert("error", "회원 가입 실패", "다시 시도해주세요");
                    }
                })
                .finally(() => setLoadingStateValue(false));
        }
    };

    const onEmailValid = () => {
        if (typeof emailInputValue !== "undefined" && emailInputValue !== "") {
            if (errors.email) {
                messageModal("사용 할 수 없는 형식입니다");
                setIsEmailValid(false);
            } else {
                postSignupEmailValid(emailInputValue)
                    .then(res => {
                        if (res === 200) {
                            messageModal("사용 가능합니다");
                            setIsEmailValid(true);
                        } else {
                            messageModal("이미 사용 중입니다");
                            setIsEmailValid(false);
                        }
                    })
            }
        } else {
            messageModal("이메일을 입력해주세요");
        }
    }
// 이메일 입력 값 변경시, 중복 확인 초기화
    useMemo(() => setIsEmailValid(false), [emailInputValue]);

    return (
        <form>
            <Stack>
                <Grid>
                    <Grid.Col span={"auto"}>
                        <TextInput {...register("email", {
                            required: "이메일을 입력해주세요",
                            pattern: {
                                value: emailRegEx,
                                message: "이메일 형식이 올바르지 않습니다",
                            }
                        })}
                                   placeholder="이메일"
                                   label="이메일"
                                   withAsterisk
                                   error={errors.email && errors.email?.message}
                                   className={`${classes["input"]} ${errors.email && "error"}`}
                        />
                    </Grid.Col>
                    <Grid.Col span={"content"}>
                        <Button className={classes["btn-primary"]} style={{marginTop: "24px"}}
                                onClick={onEmailValid}>중복확인</Button>
                    </Grid.Col>
                </Grid>
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
                    label="비밀번호"
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

                <TextInput {...register("name", {
                    required: "이름을 입력해주세요",
                    pattern: {
                        value: nameRegEX,
                        message: "이름이 올바르지 않습니다",
                    }
                })}
                           placeholder="이름"
                           label="이름"
                           withAsterisk
                           error={errors.name && errors.name?.message}
                           className={`${classes["input"]} ${errors.name && "error"}`}/>

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
                                                  asterisk={true}
                                                  label={true}
                                                  error={errors.phone?.message}/>
                            )}/>

                <Controller control={control}
                            name={"birth"}
                            render={({field: {ref, ...rest}}) => (
                                <BirthdayPicker {...rest}
                                                inputRef={ref}
                                                label={"생년월일"}
                                                placeholder={"생년월일"}/>
                            )}/>

                <TextInput {...register("address")}
                           placeholder="주소"
                           label="주소"
                           className={classes["input"]}/>

                <Checkbox
                    label={<><b style={{color: "var(--primary)"}}>[필수]</b> 서비스 이용 약관과 개인정보 취급 방침 및 개인정보 3자 제공 동의</>}
                    onClick={() => setTermOfServiceCheck(prev => !prev)}
                    className={`${classes["input-checkbox"]} signup`}/>
                {termOfServiceCheck ?
                    <Button onClick={handleSubmit(onSubmit)}
                            loading={loadingStateValue}
                            loaderPosition={"center"}
                            style={{height: "2.6rem"}}
                            className={classes["btn-primary"]}>회원가입</Button> :
                    <Button style={{height: "2.6rem"}}
                            className={`${classes["btn-primary"]} disable`}>회원가입</Button>
                }
            </Stack>
        </form>
    );
}

export default SignupForm;