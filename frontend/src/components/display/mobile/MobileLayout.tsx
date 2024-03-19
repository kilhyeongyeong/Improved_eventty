import React from "react";
import {Outlet, useLocation} from "react-router-dom";
import TopScrollBtn from "../../common/TopScrollBtn";
import Footer from "../Footer";
import MobileBottomSection from "./navbar/MobileBottomSection";
import MobileTopSection from "./header/MobileTopSection";
import MobileMenuDrawer from "./MobileMenuDrawer";
import MobileSearchDrawer from "./MobileSearchDrawer";

function MobileLayout() {
    const {pathname} = useLocation();

    return (
        <>
            <MobileTopSection/>
            <div style={{
                position: "fixed",
                bottom: "10vh",
                right: "4vw",
                zIndex: "999",
            }}>
                <TopScrollBtn/>
            </div>
            <Outlet/>
            <MobileMenuDrawer/>
            <MobileSearchDrawer/>
            {!pathname.includes("/event/") && <Footer/>}
            <MobileBottomSection/>
        </>
    );
}

export default MobileLayout;