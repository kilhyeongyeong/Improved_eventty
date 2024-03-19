import {getHostEvents} from "../service/event/fetchEvent";

export const loader = () => {
    return getHostEvents();
}