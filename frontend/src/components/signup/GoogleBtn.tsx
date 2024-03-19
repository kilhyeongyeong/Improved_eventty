import {Button} from "@mantine/core";
import customStyle from "../../styles/customStyle";
import {useGoogleLogin} from "@react-oauth/google";
import {useFetch} from "../../util/hook/useFetch";
import {ISocialLogin} from "../../types/IUser";

function GoogleBtn() {
    const {classes} = customStyle();
    const {googleLoginFetch} = useFetch();

    const login = useGoogleLogin({
        onSuccess: codeResponse => {
            const code: ISocialLogin = {
                code: codeResponse.code,
            }
            googleLoginFetch(code);
        },
        flow: "auth-code",
    });

    return (
        <Button
            className={classes["btn-google"]}
            onClick={() => login()}>
        </Button>
    );
}

export default GoogleBtn;