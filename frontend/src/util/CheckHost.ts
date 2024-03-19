import {useRecoilValue} from "recoil";
import {userState} from "../states/userState";

export const CheckHost = () => {
    return useRecoilValue(userState).isHost;
}