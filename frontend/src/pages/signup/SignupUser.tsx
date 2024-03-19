import { useSetRecoilState } from 'recoil';
import { cardTitleState } from '../../states/cardTitleState';
import { useEffect } from 'react';
import SignupForm from '../../components/signup/SignupForm';

function SignupUser() {
    const setCardTitleState = useSetRecoilState(cardTitleState);

    useEffect(() => {
        setCardTitleState("개인 회원가입");
    }, []);

    return (
        <SignupForm isHost={false} />
    );
}

export default SignupUser;