import {getKeywordEvents} from "../service/event/fetchEvent";

export const loader = ({request}:any) => {
    const url = new URL(request.url);
    const searchTerm = url.searchParams.get("keyword");

    return getKeywordEvents(searchTerm!);
}