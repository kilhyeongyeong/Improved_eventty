import React from "react";
import {Outlet} from "react-router-dom";
import TopScrollBtn from "../../common/TopScrollBtn";
import Footer from "../Footer";
import WebHeader from "./header/WebHeader";

function WebLayout() {

    return (
        <>
            <WebHeader/>
            <div style={{
                position: "fixed",
                bottom: "2vh",
                right: "2vw",
                zIndex: "999",
            }}>
                <TopScrollBtn/>
            </div>
            <Outlet/>
            <Footer/>
        </>
    );
}

export default WebLayout;