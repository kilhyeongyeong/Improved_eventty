import {useMediaQuery} from "react-responsive";
import {useMantineTheme} from "@mantine/core";

export const CheckXsSize = () => {
    return (useMediaQuery({query: `(max-width:${useMantineTheme().breakpoints.xs})`}));
}

export const CheckSmSize = () => {
    return (useMediaQuery({query: `(max-width:${useMantineTheme().breakpoints.sm})`}));
}

export const CheckMdSize = () => {
    return (useMediaQuery({query: `(max-width:${useMantineTheme().breakpoints.md})`}));
}