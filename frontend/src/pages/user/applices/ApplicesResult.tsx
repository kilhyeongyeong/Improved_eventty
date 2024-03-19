import React from "react";
import {CheckXsSize} from "../../../util/CheckMediaQuery";
import WebApplicesResult from "../../../components/user/web/applices/WebApplicesResult";
import MobileApplicesResult from "../../../components/user/mobile/applices/MobileApplicesResult";

function ApplicesResult() {
    const isMobile = CheckXsSize();
    return (
        <>
            {isMobile ? <MobileApplicesResult/> : <WebApplicesResult/>}
        </>
    );
}

export default ApplicesResult;