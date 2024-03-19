import MobileMain from "../components/main/mobile/MobileMain";
import WebMain from "../components/main/web/WebMain";
import {CheckXsSize} from "../util/CheckMediaQuery";
import {useEffect, useState} from "react";
import {IEventMain} from "../types/IEvent";
import {getMainEvents} from "../service/event/fetchEvent";
import WebMainSkeleton from "../components/main/web/skeleton/WebMainSkeleton";
import MobileMainSkeleton from "../components/main/mobile/skeleton/MobileMainSkeleton";

function Main() {
    const isMobile = CheckXsSize();
    const [DATA, setDATA] = useState<IEventMain>();

    useEffect(() => {
        getMainEvents()
            .then(res => setDATA(res));
    }, []);

    return (
        <>
            {DATA !== undefined ?
                (isMobile ? <MobileMain data={DATA}/> : <WebMain data={DATA}/>) :
                (isMobile ? <MobileMainSkeleton/> : <WebMainSkeleton/>)}
        </>
    );
}

export default Main;