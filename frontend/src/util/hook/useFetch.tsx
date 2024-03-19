import {
    deleteAccount,
    postChangePassword,
    postFindEmail,
    postFindPassword,
    postFindPasswordCallback,
    postGoogleLogin,
    postLogout,
    postNaverLogin,
    postProfile
} from "../../service/user/fetchUser";
import {MessageAlert} from "../MessageAlert";
import {useRecoilState, useResetRecoilState, useSetRecoilState} from "recoil";
import {loginState} from "../../states/loginState";
import {userState} from "../../states/userState";
import {useLocation, useNavigate} from "react-router-dom";
import {
    deleteApplyCancelEvent,
    deleteEvent,
    postApplyEvent,
    postEvent,
    postUpdateEvent
} from "../../service/event/fetchEvent";
import {IChangePW, IFindCallbackPassword, IFindEmail, IFindPassword, ISocialLogin} from "../../types/IUser";
import {modals} from "@mantine/modals";
import {menuDrawerState} from "../../states/menuDrawerState";
import {loadingState} from "../../states/loadingState";
import {IEventBooking, IEventUpdate} from "../../types/IEvent";

export function useFetch() {
    const [isLoggedIn, setIsLoggedIn] = useRecoilState(loginState);
    const setLoading = useSetRecoilState(loadingState);
    const setMenuDrawer = useSetRecoilState(menuDrawerState);
    const setUserStateValue = useSetRecoilState(userState);
    const resetUserState = useResetRecoilState(userState);
    const navigate = useNavigate();
    const {state} = useLocation();

    // 이메일 찾기
    const findEmailFetch = (data: IFindEmail) => {
        setLoading(true);

        postFindEmail(data)
            .then(res => {
                if (res.isSuccess) {
                    navigate("/find/result/email", {state: res.successResponseDTO.data});
                } else {
                    MessageAlert("error", "해당 계정을 찾을 수 없습니다", "다시 시도해주세요");
                }
            }).finally(() => setLoading(prev => !prev));
    }

    // 비밀번호 찾기
    const findPasswordFetch = (data: IFindPassword) => {
        setLoading(true);

        postFindPassword(data)
            .then(res => {
                if (res.isSuccess) {
                    navigate("/find/result/password");
                } else {
                    MessageAlert("error", "해당 계정을 찾을 수 없습니다", "다시 시도해주세요");
                }
            }).finally(() => setLoading(prev => !prev));
    }

    // 비밀번호 찾기 후 변경
    const findPasswordCallbackFetch = (data: IFindCallbackPassword) => {
        setLoading(true);

        postFindPasswordCallback(data)
            .then(res => {
                if (res.isSuccess) {
                    MessageAlert("success", "비밀번호 변경 성공", null);
                    navigate("/");
                } else {
                    MessageAlert("error", "비밀번호 변경에 실패했습니다", "다시 시도해주세요");
                }
            }).finally(() => setLoading(prev => !prev));
    }

    const googleLoginFetch = (data: ISocialLogin) => {
        setLoading(true);

        postGoogleLogin(data)
            .then(res => {
                if (res.isSuccess) {
                    const resEmail = res.successResponseDTO.data.email;
                    const resRole = res.successResponseDTO.data.role
                    const resUserId = res.successResponseDTO.data.userId;
                    const resImgPath = res.successResponseDTO.data.imagePath;

                    setIsLoggedIn((prev) => !prev);
                    setUserStateValue({
                        email: resEmail,
                        isHost: resRole === "ROLE_HOST",
                        userId: resUserId,
                        imagePath: resImgPath !== null && resImgPath,
                    });

                    sessionStorage.setItem("EMAIL", resEmail);
                    sessionStorage.setItem("AUTHORITY", resRole);
                    sessionStorage.setItem("USER_ID", resUserId);
                    sessionStorage.setItem("IMG_PATH", resImgPath);
                }
            }).catch(() => MessageAlert("error", "구글 로그인에 실패했습니다", null))
            .finally(() => setLoading(false));
    }

    const naverLoginFetch = (data: ISocialLogin) => {
        setLoading(true);

        postNaverLogin(data)
            .then(res => {
                if (res.isSuccess) {
                    const resEmail = res.successResponseDTO.data.email;
                    const resRole = res.successResponseDTO.data.role
                    const resUserId = res.successResponseDTO.data.userId;
                    const resImgPath = res.successResponseDTO.data.imagePath;

                    setIsLoggedIn((prev) => !prev);
                    setUserStateValue({
                        email: resEmail,
                        isHost: resRole === "ROLE_HOST",
                        userId: resUserId,
                        imagePath: resImgPath !== null && resImgPath,
                    });


                    window.opener.sessionStorage.setItem("EMAIL", resEmail);
                    window.opener.sessionStorage.setItem("AUTHORITY", resRole);
                    window.opener.sessionStorage.setItem("USER_ID", resUserId);
                    window.opener.sessionStorage.setItem("IMG_PATH", resImgPath);
                }
            })
            .then(() => window.opener.location.href = "/")
            .then(() => window.close())
            .catch(() => MessageAlert("error", "네이버 로그인에 실패했습니다", null))
            .finally(() => setLoading(false));
    }

    // 로그아웃
    const logoutFetch = () => {
        if (isLoggedIn) {
            postLogout()
                .then(res => {
                    if (res === 200) {
                        setIsLoggedIn(false);
                        resetUserState();
                        sessionStorage.clear();
                        MessageAlert("success", "로그아웃", null);
                    } else {
                        MessageAlert("error", "로그아웃 실패", null)
                    }
                }).finally(() => {
                navigate("/");
                setMenuDrawer(false);
            })
        }
    }

    // 내 정보 수정
    const changeProfileFetch = (data: FormData) => {
        postProfile(data)
            .then(res => {
                if (res.isSuccess) {
                    MessageAlert("success", "내 정보가 변경되었습니다", null);

                    if (res.successResponseDTO && res.successResponseDTO.data.imagePath) {
                        sessionStorage.setItem("IMG_PATH", res.successResponseDTO.data.imagePath);
                        setUserStateValue(prev => {
                            return {...prev, imagePath: res.successResponseDTO.data.imagePath}
                        })
                    } else {
                        sessionStorage.setItem("IMG_PATH", "");
                        setUserStateValue(prev => {
                            return {...prev, imagePath: ""}
                        })
                    }
                } else {
                    MessageAlert("error", "내 정보 변경 실패", null);
                }
            });
    }

    // 내 정보 비밀번호 변경
    const changePasswordFetch = (data: IChangePW) => {
        postChangePassword(data)
            .then(res => {
                if (res === 200) {
                    MessageAlert("success", "비밀번호가 변경되었습니다", null);
                } else {
                    MessageAlert("error", "비밀번호 변경 실패", null);
                }
            }).finally(() => modals.closeAll());
    }

    // 회원 탈퇴
    const deleteAccountFetch = () => {
        if (isLoggedIn) {
            deleteAccount()
                .then(res => {
                    if (res === 200) {
                        navigate("/");
                        setIsLoggedIn(false);
                        resetUserState();
                        sessionStorage.clear();
                        MessageAlert("success", "회원 탈퇴", null);
                    } else {
                        MessageAlert("error", "회원 탈퇴 실패", null);
                    }
                }).finally(() => modals.closeAll());
        }
    }

    // 행사 주최
    const createEventFetch = (data: FormData) => {
        postEvent(data)
            .then(res => {
                if (res.isSuccess) {
                    MessageAlert("success", "작성 성공", null);
                    navigate("/");
                } else {
                    MessageAlert("error", "작성 실패", null);
                }
            }).finally(() => setLoading(false));
    }

    // 행사 수정
    const updateEventFetch = (data: IEventUpdate, eventId: number) => {
        postUpdateEvent(data, eventId)
            .then(res => {
                if (res.isSuccess) {
                    MessageAlert("success", "수정 성공", null);
                    (state !== null) ? navigate(state) : navigate("/");
                } else {
                    MessageAlert("error", "작성 실패", null);
                }
            }).finally(() => setLoading(false));
    }

    // 행사 취소
    const deleteEventFetch = (data: number) => {
        deleteEvent(data)
            .then(res => {
                if (res === 200) {
                    MessageAlert("success", "행사 취소", null);
                    navigate("/");
                } else {
                    MessageAlert("error", "행사 취소 실패", null);
                }
            }).finally(() => modals.closeAll());
    }

    // 행사 예약
    const applyEventFetch = (data: IEventBooking) => {
        postApplyEvent(data)
            .then(res => {
                if (res.isSuccess) {
                    MessageAlert("success", "신청 성공", null);
                    navigate("/");
                } else {
                    MessageAlert("error", "신청 실패", "다시 시도해주세요");
                }
            }).finally(() => setLoading(false));
    }

    // 행사 예약 취소
    const applyEventCancelFetch = (data: number) => {
        deleteApplyCancelEvent(data)
            .then(res => {
                if (res.isSuccess) {
                    MessageAlert("success", "취소 성공", null);
                    navigate("/");
                } else {
                    MessageAlert("error", "취소 실패", "다시 시도해주세요");
                }
            }).finally(() => {
            setLoading(false);
            modals.closeAll();
        });
    }

    return {
        logoutFetch,
        deleteAccountFetch,
        deleteEventFetch,
        changePasswordFetch,
        changeProfileFetch,
        createEventFetch,
        findEmailFetch,
        findPasswordFetch,
        applyEventFetch,
        googleLoginFetch,
        naverLoginFetch,
        updateEventFetch,
        applyEventCancelFetch,
        findPasswordCallbackFetch
    };
}