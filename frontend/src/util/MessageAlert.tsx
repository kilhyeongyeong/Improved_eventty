import {notifications} from "@mantine/notifications";
import {IconCheck, IconExclamationMark, IconX} from "@tabler/icons-react";

type TAlert = "success" | "error" | "notice" | "info";

const TYPE_PROPERTY = {
    success: {color: "green", icon: <IconCheck/>},
    error: {color: "red", icon: <IconX/>},
    notice: {color: "yellow", icon: <IconExclamationMark/>},
    info: {color: "blue", icon: null},
}

export const MessageAlert = (type:TAlert, title:string|null, message:string|null) => {
    notifications.show({
        title: title,
        message: message,
        icon: TYPE_PROPERTY[type].icon,
        color: TYPE_PROPERTY[type].color,
        autoClose: 3000,
    })
}