import {getEvents} from "../service/event/fetchEvent";

export const loader = () => {
    return getEvents();
}