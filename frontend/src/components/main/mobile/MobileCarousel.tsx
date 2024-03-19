import React, {useRef} from "react";
import {UnstyledButton} from "@mantine/core";
import Autoplay from "embla-carousel-autoplay";
import {Carousel} from "@mantine/carousel";
import {useNavigate} from "react-router-dom";

const CAROUSEL_HEIGHT = "100vw";
const CAROUSEL_MIN_HEIGHT = "200px";
const CAROUSEL_MAX_HEIGHT = "400px";
const CAROUSEL_DELAY = 4000;
const CAROUSEL_ITEMS = [
    {color: "#F5F5F5", bgImage: "mobile_carousel_01.webp", eventId: 44},
    {color: "#03A9C3", bgImage: "mobile_carousel_02.webp", eventId: 45},
    {color: "#0C90D8", bgImage: "mobile_carousel_03.webp", eventId: 48},
    {color: "#1B1E46", bgImage: "mobile_carousel_04.webp", eventId: 47},
    {color: "#09090D", bgImage: "mobile_carousel_05.webp", eventId: 46},
];

function MobileCarousel() {
    const navigate = useNavigate();
    const autoPlay = useRef(Autoplay({delay: CAROUSEL_DELAY}));

    const items = CAROUSEL_ITEMS.map((item, idx) => (
        <Carousel.Slide key={idx}
                        style={{
                            height: CAROUSEL_HEIGHT,
                            minHeight: CAROUSEL_MIN_HEIGHT,
                            maxHeight: CAROUSEL_MAX_HEIGHT,
                        }}>
            <UnstyledButton style={{
                backgroundColor: item.color,
                backgroundImage: `url("${process.env["REACT_APP_NCLOUD_ENDPOINT"]}/${process.env["REACT_APP_NCLOUD_BUCKET_NAME"]}/main/mobile/${item.bgImage}")`,
                backgroundSize: "cover",
                backgroundRepeat: "no-repeat",
                backgroundPosition: "center",
                height: "100%",
                width: "100%",
                borderRadius: "1rem",
            }}
                            onClick={() => navigate(`/event/${item.eventId}`)}
            />
        </Carousel.Slide>
    ));

    return (
        <Carousel slideSize={"80%"}
                  height={CAROUSEL_HEIGHT}
                  mih={CAROUSEL_MIN_HEIGHT}
                  mah={CAROUSEL_MAX_HEIGHT}
                  draggable
                  withControls
                  loop
                  slidesToScroll={1}
                  slideGap={"1rem"}
                  controlSize={30}
                  align={"center"}
                  plugins={[autoPlay.current]}
                  onMouseEnter={autoPlay.current.stop}
                  onMouseLeave={autoPlay.current.reset}
                  style={{marginTop: "3vh"}}
        >
            {items}
        </Carousel>
    );
}

export default MobileCarousel;