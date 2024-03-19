import {getEvent} from "../service/event/fetchEvent";

export const loader = ({params}:any) => {
    return getEvent(params.eventId);
}
