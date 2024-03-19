import React, {useEffect, useState} from "react";
import {Table} from "@mantine/core";
import {IEventApplicesResult} from "../../../../types/IEvent";
import {MessageAlert} from "../../../../util/MessageAlert";

function WebApplicesTable({data}: { data: IEventApplicesResult[] }) {
    const [rows, setRows] = useState<JSX.Element[] | null>(null);

    useEffect(() => {
        if (data !== undefined) {
            setRows(data.map(item => (
                <tr key={item.applyId}>
                    <td>{item.applyId}</td>
                    <td>{item.name}</td>
                    <td>{item.phone}</td>
                    <td>{item.price.toLocaleString("ko")} 원</td>
                    <td>{new Date(item.date).toLocaleDateString()}</td>
                    <td>{item.state}</td>
                </tr>
            )));
        }
    }, [data]);

    return (
        <Table highlightOnHover>
            <thead>
            <tr>
                <th>예약 번호</th>
                <th>이름</th>
                <th>휴대폰 번호</th>
                <th>가격</th>
                <th>일시</th>
                <th>상태</th>
            </tr>
            </thead>
            <tbody>
                {data !== undefined ? rows : <tr></tr>}
            </tbody>
        </Table>
    );
}

export default WebApplicesTable;