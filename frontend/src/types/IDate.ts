import React from "react";

export interface IDatePicker {
    label?: string;
    minDate?: Date;
    maxYear?: number;
    placeholder?: string;
    value: Date | undefined;
    inputRef: React.Ref<any>;
    defaultValue? : Date;
    onChange: (newValue: Date) => void;
    error?: string;
}

export interface IDateTimePicker {
    label?: string;
    minDate?: Date;
    maxYear?: number;
    placeholder?: string;
    value: Date | undefined;
    onChange: (newValue: Date) => void;
    error?: string;
    valueFormat?: string;
}