import CardForm from '../components/signup/CardForm';
import { Outlet } from 'react-router-dom';

function Signup() {
    return (
        <CardForm>
            <Outlet />
        </CardForm>
    );
}

export default Signup;