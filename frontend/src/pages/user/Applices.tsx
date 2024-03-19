import React from "react";
import {CheckXsSize} from "../../util/CheckMediaQuery";
import WebApplicesLayout from "../../components/user/web/applices/WebApplicesLayout";
import MobileApplicesLayout from "../../components/user/mobile/applices/MobileApplicesLayout";

function Applices() {
    const isMobile = CheckXsSize();

    return (
        <>
            {isMobile ? <MobileApplicesLayout/> : <WebApplicesLayout/>}
        </>
    );
}

export default Applices;