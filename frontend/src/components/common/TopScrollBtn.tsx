import React, {useEffect, useState} from "react";
import {ActionIcon, useMantineTheme} from "@mantine/core";
import {IconArrowBarToUp} from "@tabler/icons-react";
import {useMediaQuery} from "react-responsive";

const SCROLL_Y = 370;

function TopScrollBtn() {
    const mobile = useMediaQuery({query: `(max-width:${useMantineTheme().breakpoints.xs})`});
    const [showButton, setShowButton] = useState(false);
    const scrollToTop = () => {
        window.scrollTo({
            top: 0,
            behavior: "smooth",
        })
    }

    useEffect(() => {
        const handleShowButton = () => {
            setShowButton((window.scrollY > SCROLL_Y));
        }

        window.addEventListener("scroll", handleShowButton);
        return ()=>{
            window.removeEventListener( "scroll", handleShowButton);
        }
    }, []);

    return (
        <>
            {showButton &&
                <ActionIcon
                    variant={"outline"}
                    radius={"xl"}
                    onClick={scrollToTop}
                    style={{
                        width: `${mobile ? "13vw" : "50px"}`,
                        height: `${mobile ? "13vw" : "50px"}`,
                        opacity: "0.4",
                        background: "white",
                    }}>
                    <IconArrowBarToUp/>
                </ActionIcon>
            }
        </>
    );
}

export default TopScrollBtn;