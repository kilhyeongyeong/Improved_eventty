import { useSetRecoilState } from 'recoil';
import { cardTitleState } from '../../states/cardTitleState';
import { useEffect } from 'react';
import SignupForm from '../../components/signup/SignupForm';

function SignupHost() {
    const setCardTitleState = useSetRecoilState(cardTitleState);

    useEffect(() => {
        setCardTitleState("주최자 회원가입");
    }, []);

    return (
        <SignupForm isHost={true} />
    );
}

export default SignupHost; 