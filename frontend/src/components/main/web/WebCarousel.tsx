import React, {useRef} from "react";
import {UnstyledButton} from "@mantine/core";
import Autoplay from "embla-carousel-autoplay";
import {Carousel} from "@mantine/carousel";
import {useNavigate} from "react-router-dom";

const CAROUSEL_HEIGHT = "35vw";
const CAROUSEL_MIN_HEIGHT = "230px";
const CAROUSEL_MAX_HEIGHT = "400px";
const CAROUSEL_DELAY = 4000;
const CAROUSEL_ITEMS = [
    {color: "#F5F5F5", bgImage: "web_carousel_01.webp", eventId: 44},
    {color: "#03A9C3", bgImage: "web_carousel_02.webp", eventId: 45},
    {color: "#0C90D8", bgImage: "web_carousel_03.webp", eventId: 48},
    {color: "#1B1E46", bgImage: "web_carousel_04.webp", eventId: 47},
    {color: "#09090D", bgImage: "web_carousel_05.webp", eventId: 46},
];

function WebCarousel() {
    const navigate = useNavigate();
    const autoPlay = useRef(Autoplay({delay: CAROUSEL_DELAY}));

    const items = CAROUSEL_ITEMS.map((item, idx) => (
        <Carousel.Slide key={idx}
                        style={{
                            height: CAROUSEL_HEIGHT,
                            minHeight: CAROUSEL_MIN_HEIGHT,
                            maxHeight: CAROUSEL_MAX_HEIGHT,
                            backgroundColor: item.color,
                        }}>
            <UnstyledButton style={{
                backgroundImage: `url("${process.env["REACT_APP_NCLOUD_ENDPOINT"]}/${process.env["REACT_APP_NCLOUD_BUCKET_NAME"]}/main/web/${item.bgImage}")`,
                backgroundSize: "auto 100%",
                backgroundRepeat: "no-repeat",
                backgroundPosition: "center",
                width: "100%",
                height: "100%",
            }}
                            onClick={() => navigate(`/event/${item.eventId}`)}
            />
        </Carousel.Slide>
    ));

    return (
        <Carousel slideSize={"100%"}
                  sx={{flex: 1}}
                  draggable
                  withControls
                  withIndicators
                  loop
                  plugins={[autoPlay.current]}
                  onMouseEnter={autoPlay.current.stop}
                  onMouseLeave={autoPlay.current.reset}
                  style={{
                      height: CAROUSEL_HEIGHT,
                      minHeight: CAROUSEL_MIN_HEIGHT,
                      maxHeight: CAROUSEL_MAX_HEIGHT,
                  }}
                  styles={{
                      indicator: {
                          width: "0.5rem",
                          height: "0.5rem",
                          transition: "width 250ms ease",
                          "&[data-active]": {
                              width: "2.5rem",
                          },
                      },
                  }}
        >
            {items}
        </Carousel>
    );
}

export default WebCarousel;