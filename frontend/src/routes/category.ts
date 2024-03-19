import {getCategoryEvents} from "../service/event/fetchEvent";

export const loader = ({params}:any) => {
    return getCategoryEvents(params.category);
}
