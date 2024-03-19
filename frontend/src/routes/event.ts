import {getEvent} from "../service/event/fetchEvent";

// params에 any 타입은 임시방편
export const loader = ({params}: any) => {
    return getEvent(params.eventId);
}
