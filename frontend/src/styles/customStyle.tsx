import {createStyles} from '@mantine/core';

const customStyle = createStyles((theme) => ({
    "header": {
        zIndex: 1000,
        position: "relative",
        border: "none",
        "&.mobile-event-detail": {
            position: "sticky",
        }
    },
    "input": {
        ".mantine-Input-input": {
            borderColor: "#cdcdcd",
            color: "#000000 !important",
            "::placeholder": {
                color: "#cdcdcd !important",
            },
            ":focus": {
                borderColor: "var(--primary)",
            },
        },
        "&.error": {
            input: {
                ":focus": {
                    borderColor: "red",
                },
            },
        },
        "&.search": {
            width: "100%",
            input: {
                fontSize: "1.1rem",
                ":focus": {
                    borderWidth: "2px",
                },
            },
        },
    },
    "input-textarea": {
        ".mantine-Textarea-input": {
            borderColor: "#cdcdcd",
            color: "#000000 !important",
            "::placeholder": {
                color: "#cdcdcd !important",
            },
            ":focus": {
                borderColor: "var(--primary)",
            },
        },
        "&.error": {
            ".mantine-Textarea-input": {
                ":focus": {
                    borderColor: "red",
                },
            },
        },
    },
    "input-date": {
        ".mantine-DatePickerInput-input": {
            ":active, :focus": {
                borderColor: "var(--primary)",
            },
        },
        ".mantine-DatePickerInput-day": {
            "&[data-selected], &[data-selected]:hover": {
                background: "var(--primary)",
            },
            "&[data-items='5']": {
                background: "red",
            }
        },
        ".mantine-DatePickerInput-placeholder": {
            color: "#cdcdcd !important",
        },
    },
    "input-date-time": {
        ".mantine-DateTimePicker-input": {
            ":active, :focus": {
                borderColor: "var(--primary)",
            },
        },
        ".mantine-DateTimePicker-day": {
            "&[data-selected], &[data-selected]:hover": {
                background: "var(--primary)",
            },
            "&[data-items='5']": {
                background: "red",
            }
        },
        ".mantine-DateTimePicker-placeholder": {
            color: "#cdcdcd !important",
        },
    },
    "input-select": {
        input: {
            borderColor: "#cdcdcd",
            color: "#000000 !important",
            "::placeholder": {
                color: "#cdcdcd !important",
            },
            ":focus": {
                borderColor: "var(--primary)",
            },
        },
        ".mantine-Select-item": {
            "&[data-selected]": {
                backgroundColor: "var(--primary) !important",
                "&[data-hovered]": {
                    filter: "brightness(0.96)",
                },
            },
        },
    },
    "input-checkbox": {
        ".mantine-Checkbox-input:checked": {
            background: "var(--primary)",
            borderColor: "var(--primary)",
        },
        "&.signup": {
            label: {
                fontSize: "0.5rem",
            },
        },
        "&.login": {
            label: {
                color: "#666666",
            },
        },
    },
    "btn-primary": {
        backgroundColor: "var(--primary)",
        ":hover": {
            backgroundColor: "var(--primary)",
            filter: "brightness(0.96)",
        },
        "&.disable": {
            background: "#e6e6e6",
            color: "#b3b3b3",
            pointerEvents: "none",
        },
    },
    "btn-primary-outline": {
        backgroundColor: "white !important",
        borderColor: "var(--primary) !important",
        color: "var(--primary) !important",
        ":hover": {
            filter: "brightness(0.97)",
        }
    },
    "btn-gray": {
        backgroundColor: "#b3b3b3",
        cursor: "default",
    },
    "btn-gray-outline": {
        backgroundColor: "white !important",
        borderColor: "#b3b3b3 !important",
        color: "#666666 !important",
        ":hover": {
            filter: "brightness(0.97)",
        },
        "&.non":{
            ":hover":{
                filter: "none",
            },
        },
    },
    "btn-naver":{
        backgroundColor: "#03c75a !important",
        color: "#ffffff !important",
        height: "50px",
        width: "50px",
        backgroundImage: `url(${process.env.PUBLIC_URL}/images/naver_normal.svg)`,
        backgroundRepeat: "no-repeat",
        backgroundPosition: "center",
        backgroundSize: "25px",
        ":hover": {
            filter: "brightness(0.97)",
        },
    },
    "btn-google":{
        backgroundColor: "white !important",
        borderColor: "#b3b3b3 !important",
        color: "#666666 !important",
        height: "50px",
        width: "50px",
        backgroundImage: `url(${process.env.PUBLIC_URL}/images/google_normal.svg)`,
        backgroundRepeat: "no-repeat",
        backgroundPosition: "center",
        backgroundSize: "26px",
        ":hover": {
            filter: "brightness(0.97)",
        },
    },
    "radio-primary":{
        ".mantine-Radio-radio:checked":{
            background: "var(--primary)",
            borderColor: "var(--primary)",
        },
    },
    "signup-footer": {
        fontSize: "0.8rem",
        color: "#666666",
        paddingTop: "1rem",
    },
    "signup-divider": {
        textAlign: "center",
        whiteSpace: "pre-line",
        padding: "3rem 0 1rem 0",
        color: "#666666",
    },
    "web-nav-link": {
        fontWeight: "bold",
        fontSize: "1.2rem",
        padding: "0.5rem",
        height: "100%",
        ":hover": {
            color: "var(--primary)",
        }
    },
    "mobile-nav-link": {
        textAlign: "center",
        ":active": {
            color: "var(--primary)",
        },
        "&.active": {
            color: "var(--primary)",
        },
    },
    "ticket-select": {
        ":hover": {
            cursor: "pointer",
            borderColor: "var(--primary)",
            transition: "0.1s ease"
        },
        "&.selected":{
            border: "2px solid var(--primary) !important",
            backgroundColor: "var(--primary-light)",
        },
    },
    "category-scroll": {
        overflowX: "scroll",
        msOverflowStyle: "none",
        scrollbarWidth: "none",
        "&::-webkit-scrollbar": {
            width: 0,
        }
    },
    "tabs-primary": {
        ".mantine-Tabs-tab[data-active]": {
            borderBottom: "3px solid var(--primary) !important",
        },
    },
    "web-menu-drawer": {
        visibility: "visible",
        transition: "all",
        ".mantine-Drawer-inner": {
            zIndex: 999,
            height: "250px",
        },
        "&.hidden": {
            visibility: "hidden",
        }
    },
}))

export default customStyle;