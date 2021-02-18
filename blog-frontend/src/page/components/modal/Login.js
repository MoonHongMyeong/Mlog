import React, { useEffect } from 'react'
import styled from 'styled-components';
import { LongButton } from '../atoms/Buttons';
import { ModalBackLayout } from '../atoms/Layouts'

const body = document.querySelector('#root');

export default function Login() {
  useEffect(() => {
    body.setAttribute("style", "overflow: hidden;");
    return () => {
      body.removeAttribute("style");
    }
  }, [])

  return (
    <>
      <ModalBackLayout>
        <ModalLayout>
          <GoogleButton color={"#cf4332;"}>
            <i className="fab fa-google"></i> Google 계정으로 로그인
        </GoogleButton>
          <NaverButton color={"#27ae60"}>
            <span>N</span> naver 계정으로 로그인
        </NaverButton>
        </ModalLayout>
      </ModalBackLayout>
    </>
  )
}


const ModalLayout = styled.div`
  width : 500px;
  display : flex;
  flex-direction : column;
  justify-content:center;
  align-items : center;
  background-color : #fafafa;
  box-shadow : 0 4px 8px 0;
  height : 10rem;
  border-radius : 10px;

  @media screen and (max-width : 550px){
    width : calc(100% - 2rem);
  }

`;
const GoogleButton = styled(LongButton)`
  width : 90%;
  margin : 0.5rem;
`;

const NaverButton = styled(LongButton)`
  width : 90%;
  margin : 0.5rem;
`;