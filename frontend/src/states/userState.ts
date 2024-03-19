import { atom } from 'recoil';

export const userState = atom({
    key: "userState",
    default: {
        email: "",
        isHost: false,
        userId: 0,
        imagePath: "",
    },
})