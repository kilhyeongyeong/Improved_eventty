import React, {useEffect, useState} from "react";
import {useRecoilState, useRecoilValue, useSetRecoilState} from "recoil";
import {searchDrawerState} from "../states/searchDrawerState";
import {Outlet, ScrollRestoration, useLocation, useNavigation} from "react-router-dom";
import {menuDrawerState} from "../states/menuDrawerState";
import {Notifications} from "@mantine/notifications";
import {useMediaQuery} from "react-responsive";
import {LoadingOverlay, useMantineTheme} from "@mantine/core";
import {loadingState} from "../states/loadingState";
import {loginState} from "../states/loginState";
import {userState} from "../states/userState";
import {eventTicketDrawerState} from "../states/eventTicketDrawerState";
import {ModalsProvider} from "@mantine/modals";

function RootSetStates() {
    const {state} = useNavigation();
    const {pathname} = useLocation();
    const loadingValue = useRecoilValue(loadingState);
    const [loginStateValue, setloginStateValue] = useRecoilState(loginState);
    const setUserState = useSetRecoilState(userState);
    const setSearchDrawer = useSetRecoilState(searchDrawerState);
    const setEventTicketDrawer = useSetRecoilState(eventTicketDrawerState);
    const setMenuDrawer = useSetRecoilState(menuDrawerState);
    const [loadingOverlay, setLoadingOverlay] = useState(false);
    const mobile = useMediaQuery({query: `(max-width:${useMantineTheme().breakpoints.xs})`});

    useEffect(() => {
        return () => {
            setSearchDrawer(false);
            setMenuDrawer(false);
            setEventTicketDrawer(false);
        }
    }, [pathname]);

    // 로그인 확인
    useEffect(() => {
        const email = sessionStorage.getItem("EMAIL");
        const authority = sessionStorage.getItem("AUTHORITY");
        const userId = Number(sessionStorage.getItem("USER_ID"));
        const imgPath = sessionStorage.getItem("IMG_PATH");

        if (email && authority && !loginStateValue) {
            setloginStateValue(true);
            setUserState({
                email: email,
                isHost: authority === "ROLE_HOST",
                userId: userId,
                imagePath: imgPath !== null ? imgPath : "",
            });
        }
    });

    // 일정 시간 뒤 로딩 화면 보여주기
    useEffect(() => {
        const loadingTimer = setTimeout(() => {
            if (state === "loading" || loadingValue) {
                setLoadingOverlay(true);
            }else{
                setLoadingOverlay(false);
            }
        }, 600);

        return(() => clearTimeout(loadingTimer))
    }, [state, loadingValue]);

    return (
        <>
            <ModalsProvider>
                {/* Router Loader 로딩 오버레이 */}
                <LoadingOverlay visible={loadingOverlay}
                                loaderProps={{size: "md", color: "var(--primary)", variant: "dots"}}
                                overlayBlur={1}
                                style={{position: "fixed"}}
                                zIndex={1002}
                />
                <Notifications position={mobile ? "top-center" : "bottom-right"} zIndex={"1003"}/>
                <ScrollRestoration/>
                <Outlet/>
            </ModalsProvider>
        </>
    );
}

export default RootSetStates;