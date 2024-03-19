import React from "react";
import {DatePickerInput} from "@mantine/dates";
import customStyle from "../../styles/customStyle";
import {IDatePicker} from "../../types/IDate";

function BirthdayPicker(props:IDatePicker) {
    const {classes} = customStyle();
    const currentDate = new Date();

    return (
        <DatePickerInput label={props.label}
                         placeholder={props.placeholder}
                         defaultLevel={"year"}
                         valueFormat={"YYYY-MM-DD"}
                         value={props.value}
                         onChange={props.onChange}
                         weekendDays={[0]}
                         firstDayOfWeek={0}
                         maxDate={new Date(currentDate.getFullYear()-14, currentDate.getMonth(), currentDate.getDate())}
                         getDayProps={(date) => {
                             if (date.getDay() === 6) {
                                 return {
                                     sx: () => ({
                                         color: "#3381ff",
                                     }),
                                 };
                             }
                             return {};
                         }}
                         locale={"ko"}
                         ref={props.inputRef}
                         error={props.error}
                         className={classes["input-date"]}/>
    );
}

export default BirthdayPicker;