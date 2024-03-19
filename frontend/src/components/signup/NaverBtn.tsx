import {Button} from "@mantine/core";
import customStyle from "../../styles/customStyle";

function NaverBtn() {
    const {classes} = customStyle();
    const handleOnClick = () => {
        window.open(`https://nid.naver.com/oauth2.0/authorize?client_id=${process.env["REACT_APP_NAVER_CLIENT_ID"]}&response_type=code&redirect_uri=${process.env["REACT_APP_NAVER_REDIRECT_URL"]}`, "eventty", "_blank");
    }

    return (
        <Button onClick={() => handleOnClick()}
                className={classes["btn-naver"]}/>
    );
}

export default NaverBtn;