export interface ISignup {
    email: string;
    password: string;
    passwordConfirm?: string;
    name: string;
    phone: string;
    birth?: Date;
    address?: string;
}

export interface ILogin {
    email: string;
    password: string;
}

export interface ISocialLogin {
    code: string;
}

export interface IUser {
    userId: number;
    name: string;
    address?: string;
    birth?: Date;
    imagePath?: string;
    imageId?: number;
    originFileName?: string;
    phone: string;
}

export interface IUpdateUser {
    [key: string]: string | any;

    image?: File | null;
    imageId?: number;
    name: string;
    phone: string;
    address?: string;
    birth?: Date;
    isUpdate: boolean;
}

export interface IChangePW {
    password: string;
    passwordConfirm?: string;
}

export interface IFindEmail {
    name: string;
    phone: string;
}

export interface IFindPassword {
    email: string;
    name: string;
    phone: string;
}

export interface IFindCallbackPassword {
    password: string;
    passwordConfirm?: string;
}