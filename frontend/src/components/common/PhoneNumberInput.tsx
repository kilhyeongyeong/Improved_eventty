import React, {ChangeEvent, useState} from "react";
import {TextInput} from "@mantine/core";
import customStyle from "../../styles/customStyle";

interface IPhoneNumber {
    value: string;
    onChange: () => void;
    onBlur: () => void;
    inputRef: React.Ref<any>;
    asterisk?: boolean;
    error?: string;
    label?: boolean;
    description?: boolean;
    width?: string;
}

function PhoneNumberInput({value, onChange, onBlur, inputRef, error, asterisk, label, description, width}: IPhoneNumber) {
    const {classes} = customStyle();

    const [phoneValue, setPhoneValue] = useState(value || "");
    const handlePhoneInputChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {value} = e.target;
        const formattedValue = value
            .replace(/[^0-9]/g, '')
            .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3")
            .replace(/(\-{1,2})$/g, "");
        setPhoneValue(formattedValue);
    }

    return (
        <TextInput placeholder={"휴대폰 번호"}
                   label={label && "휴대폰 번호"}
                   description={description && "휴대폰 번호"}
                   withAsterisk={asterisk}
                   maxLength={13}
                   value={phoneValue}
                   onChange={onChange}
                   onBlur={onBlur}
                   ref={inputRef}
                   onInput={handlePhoneInputChange}
                   error={error}
                   style={{width: width}}
                   className={`${classes["input"]} ${error && "error"}`}/>
    );
}

export default PhoneNumberInput;