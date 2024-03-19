import React, {useEffect} from "react";
import {useLocation, useParams} from "react-router-dom";
import CardForm from "../../components/signup/CardForm";
import {useSetRecoilState} from "recoil";
import {cardTitleState} from "../../states/cardTitleState";
import FindResultEmail from "./FindResultEmail";
import FindResultPassword from "./FindResultPassword";

function FindResult() {
    const {state} = useLocation();
    const {params} = useParams();
    const setCardTitle = useSetRecoilState(cardTitleState);

    useEffect(() => {
        setCardTitle("조회 결과");
    }, []);

    return (
        <CardForm>
            {/*{!state && <Navigate to={"/"}/>}*/}
            {params === "email" ?
                <FindResultEmail/> :
                <FindResultPassword/>
            }
        </CardForm>
    );
}

export default FindResult;