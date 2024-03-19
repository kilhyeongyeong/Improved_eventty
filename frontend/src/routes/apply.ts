import {CheckHost} from "../util/CheckHost";
import {redirect} from "react-router-dom";
import {getApplyEvent} from "../service/event/fetchEvent";

export const loader = () => {
    return getApplyEvent();
}