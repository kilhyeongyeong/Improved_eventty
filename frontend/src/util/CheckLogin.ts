import {useRecoilValue} from "recoil";
import {loginState} from "../states/loginState";

export const CheckLogin = () => {
  return useRecoilValue(loginState);
}