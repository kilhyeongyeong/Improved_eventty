import React from "react";
import {CheckXsSize} from "../../util/CheckMediaQuery";
import WebHostRegister from "../../components/user/web/WebHostRegister";
import MobileHostRegister from "../../components/user/mobile/MobileHostRegister";

function Registers() {
    const isMobile = CheckXsSize();

    return (
        <>
            {isMobile ? <MobileHostRegister/> : <WebHostRegister/>}
        </>
    );
}

export default Registers;