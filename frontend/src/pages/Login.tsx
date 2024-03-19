import {Stack, Button, TextInput, Flex, Divider, Text, Group} from "@mantine/core";
import CardForm from "../components/signup/CardForm";
import {useForm} from "react-hook-form";
import {Link} from "react-router-dom";
import {useRecoilState, useSetRecoilState} from 'recoil';
import {cardTitleState} from '../states/cardTitleState';
import {useEffect} from 'react';
import {userState} from '../states/userState';
import {loginState} from '../states/loginState';
import customStyle from "../styles/customStyle";
import {ILogin} from "../types/IUser";
import {postLogin} from "../service/user/fetchUser";
import GoogleBtn from "../components/signup/GoogleBtn";
import {loadingState} from "../states/loadingState";
import NaverBtn from "../components/signup/NaverBtn";

enum ERROR_MESSAGE {
    email = "이메일을 입력해주세요",
    password = "비밀번호를 입력해주세요",
    fail = "이메일 혹은 비밀번호가 일치하지 않습니다. \n입력한 내용을 다시 확인해 주세요.",
}

function Login() {
    const setIsLoggedIn = useSetRecoilState(loginState);
    const [loading, setLoading] = useRecoilState(loadingState);
    const setUsersStateValue = useSetRecoilState(userState);

    const {register, handleSubmit, setFocus, setError, formState: {errors}} = useForm<ILogin>();
    const onSubmit = (data: ILogin) => {
        if (!data.email || !data.password) {
            const field = !data.email ? "email" : "password";
            setError(field, {message: ERROR_MESSAGE[field]});
            setFocus(field);
            return;
        }

        setLoading(true);
        postLogin(data)
            .then(res => {
                if (res.isSuccess) {
                    const resEmail = res.successResponseDTO.data.email;
                    const resRole = res.successResponseDTO.data.role
                    const resUserId = res.successResponseDTO.data.userId;
                    const resImgPath = res.successResponseDTO.data.imagePath;

                    setIsLoggedIn((prev) => !prev);
                    setUsersStateValue({
                        email: resEmail,
                        isHost: resRole === "ROLE_HOST",
                        userId: resUserId,
                        imagePath: resImgPath !== null && resImgPath,
                    });

                    sessionStorage.setItem("EMAIL", resEmail);
                    sessionStorage.setItem("AUTHORITY", resRole);
                    sessionStorage.setItem("USER_ID", resUserId);
                    sessionStorage.setItem("IMG_PATH", resImgPath);
                } else {
                    setError("root", {message: ERROR_MESSAGE["fail"]});
                }
            }).catch(res => console.error(res))
            .finally(() => setLoading(false));
    };

    const {classes} = customStyle();

    const setCardTitleState = useSetRecoilState(cardTitleState);
    useEffect(() => {
        setCardTitleState("로그인");
    }, []);

    return (
        <CardForm>
            <form onSubmit={handleSubmit(onSubmit)}>
                <Stack>
                    <TextInput {...register("email")}
                               placeholder="이메일"
                               className={classes["input"]}
                    />
                    <TextInput {...register("password")}
                               type="password"
                               placeholder="비밀번호"
                               className={classes["input"]}
                    />

                    {/* 자동 로그인 */}
                    {/*<Checkbox label={"자동 로그인"}
                              style={{marginBottom: "0.5rem"}}
                              className={`${classes["input-checkbox"]} login`}/>*/}

                    {/* 에러 메세지 */}
                    <Text fz={"0.75rem"}
                          color={"#f44336"}
                          style={{whiteSpace: "pre-wrap"}}>
                        {errors.email?.message || errors.password?.message || errors.root?.message}
                    </Text>

                    <Button type="submit"
                            style={{height: "2.6rem"}}
                            className={classes["btn-primary"]}>
                        로그인
                    </Button>
                    <Flex gap={"xs"} align={"center"} justify={"center"} className={classes["signup-footer"]}>
                        <Link to={"/signup"}>회원가입</Link>|
                        <Link to={"/find/email"}>계정 찾기</Link>|
                        <Link to={"/find/password"}>비밀번호 찾기</Link>
                    </Flex>
                    <Divider my={"xs"} labelPosition={"center"} label={"SNS 로그인"}
                             className={classes["signup-divider"]}/>
                    <Group noWrap position={"center"}>
                        <GoogleBtn/>
                        <NaverBtn/>
                    </Group>
                </Stack>
            </form>
        </CardForm>
    )
}

export default Login;