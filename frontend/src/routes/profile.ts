import {getProfile} from "../service/user/fetchUser";

export const loader = () => {
    return getProfile();
}