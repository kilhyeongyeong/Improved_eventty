import {getMainEvents} from "../service/event/fetchEvent";

export const loader = () => {
    return getMainEvents();
}