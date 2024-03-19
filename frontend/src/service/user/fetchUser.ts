import {
    IChangePW,
    IFindCallbackPassword,
    IFindEmail,
    IFindPassword,
    ILogin,
    ISignup,
    ISocialLogin
} from "../../types/IUser";
import {GetCsrfToken, SetCsrfToken, SetSnsCsrfToken} from "../../util/UpdateToken";
import {redirect} from "react-router-dom";

export const postSignupEmailValid = async (data: string) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/email`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then(res => res.status)
        .catch(res => console.error(res));
}

export const postSignupUser = async (data: ISignup) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/me/user`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then((res) => res.status)
        .catch(res => console.error(res));
}
export const postSignupHost = async (data: ISignup) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/me/host`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then((res) => res.status)
        .catch(res => console.error(res));
}

// 이메일 찾기
export const postFindEmail = async (data: IFindEmail) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/find/email`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then((res) => res.json())
        .catch(res => console.error(res));
}

// 비밀번호 찾기
export const postFindPassword = async (data: IFindPassword) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/find/password`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then((res) => res.json())
        .catch(res => console.error(res));
}

// 비밀번호 찾기 후 변경
export const postFindPasswordCallback = async (data: IFindCallbackPassword) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/find/password/callback`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then((res) => res.json())
        .catch(res => console.error(res));
}

export const postLogin = async (data: ILogin) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/login`, {
        method: "POST",
        credentials: "include",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then((res) => {
            SetCsrfToken(res);
            return res.json();
        });
}

// Google 로그인
export const postGoogleLogin = async (data: ISocialLogin) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/oauth/login/google`, {
        method: "POST",
        credentials: "include",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then((res) => {
            SetCsrfToken(res);
            return res.json();
        });
}

// Naver 로그인
export const postNaverLogin = async (data: ISocialLogin) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/oauth/login/naver`, {
        method: "POST",
        credentials: "include",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then((res) => {
            SetSnsCsrfToken(res);
            return res.json();
        });
}

export const postLogout = async () => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/logout`, {
        method: "POST",
        credentials: "include",
        headers: {"Content-Type": "application/json", "X-Csrf-Token": GetCsrfToken()!},
    })
        .then(res => res.status)
        .catch(res => console.error(res));
}

export const getProfile = async () => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/user/secret/users/me`, {
        method: "GET",
        credentials: "include",
        headers: {"Content-Type": "application/json", "X-Csrf-Token": GetCsrfToken()!},
    })
        .then((res) => {
            SetCsrfToken(res);
            return res.json();
        })
        .then((res) => res.successResponseDTO.data)
        .catch(res => {
            SetCsrfToken(res);
            redirect("/login");
        });
}

export const postProfile = async (data: FormData) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/user/secret/users/me`, {
        method: "POST",
        credentials: "include",
        headers: {"X-Csrf-Token": GetCsrfToken()!},
        body: data,
    })
        .then((res) => {
            SetCsrfToken(res);
            return res.json();
        })
        .catch(res => SetCsrfToken(res));
}

export const postChangePassword = async (data: IChangePW) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/change/password`, {
        method: "POST",
        credentials: "include",
        headers: {"Content-Type": "application/json", "X-Csrf-Token": GetCsrfToken()!},
        body: JSON.stringify(data),
    })
        .then((res) => {
            SetCsrfToken(res);
            return res.status;
        })
        .catch(res => SetCsrfToken(res));
}

export const deleteAccount = async () => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/me`, {
        method: "DELETE",
        headers: {"X-Csrf-Token": GetCsrfToken()!},
    })
        .then((res) => {
            SetCsrfToken(res);
            return res;
        })
        .then((res) => res.status)
        .catch(res => {
            SetCsrfToken(res);
        });
}