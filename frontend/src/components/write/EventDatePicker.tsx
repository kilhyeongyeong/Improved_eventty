import React, {useRef} from "react";
import {IconCalendar, IconClock} from "@tabler/icons-react";
import {DateTimePicker} from "@mantine/dates";
import customStyle from "../../styles/customStyle";
import {IDateTimePicker} from "../../types/IDate";
import {ActionIcon} from "@mantine/core";

function EventDatePicker(props: IDateTimePicker) {
    const {classes} = customStyle();
    const ref = useRef<HTMLInputElement>(null);

    // Set seconds to 0
    const setTimeSecondsToZero = (time: Date) => {
        time && time.setSeconds(0);
        return time;
    };

    return (
        <DateTimePicker
            icon={<IconCalendar/>}
            clearable
            value={props.value}
            onChange={(newValue: Date) => props.onChange(setTimeSecondsToZero(newValue))}
            onTimeUpdate={() => props.value?.setSeconds(0)}
            valueFormat={props.valueFormat ? props.valueFormat : "YYYY-MM-DD HH시 mm분"}
            placeholder={props.placeholder}
            weekendDays={[0]}
            firstDayOfWeek={0}
            minDate={props.minDate}
            getDayProps={(date) => {
                if (date.getDay() === 6) {
                    return {
                        sx: () => ({ color: "#3381ff"}),
                    };
                }
                return {};
            }}
            timeInputProps={{
                "ref": ref,
                rightSection:
                    <ActionIcon onClick={() => ref.current?.showPicker()}>
                        <IconClock/>
                    </ActionIcon>,
                styles: {
                    input: {
                        ":active, :focus": {
                            borderColor: "var(--primary)",
                        },
                    }
                },
            }}
            error={props.error}
            locale={"ko"}
            style={{width: "15rem"}}
            className={classes["input-date-time"]}/>
    );
}

export default EventDatePicker;