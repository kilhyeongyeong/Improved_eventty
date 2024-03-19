import {getProfile} from "../service/user/fetchUser";
import {CheckHost} from "../util/CheckHost";
import {redirect} from "react-router-dom";

export const loader = () => {
    return getProfile();
}