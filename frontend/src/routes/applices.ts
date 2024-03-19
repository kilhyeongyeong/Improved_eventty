import {getApplyUsers} from "../service/event/fetchEvent";

export const loader = ({params}:any) => {
    const state = "0";
    const request = {
        eventId: params.eventId.toString(),
        state: state,
        order: "0",
    }
    return getApplyUsers(new URLSearchParams(request).toString());
}
