import React, { useEffect } from 'react'
import styled from 'styled-components';
import { LongButton } from '../atoms/Buttons';
import { ModalBackLayout } from '../atoms/Layouts'
import axios from 'axios';

const body = document.querySelector('#root');

export default function Login(props) {
  useEffect(() => {
    body.setAttribute("style", "overflow: hidden;");
    return () => {
      body.removeAttribute("style");
    }
  }, [])

  const googleLogin = () => {
    axios.get('/oauth2/authorization/google', { header: { 'Access-Control-Allow-Origin': '*' } })
      .then(response => {
        console.log(response);
        window.open()
      })
  }

  return (
    <>
      <ModalBackLayout>
        <ModalLayout>
          <div style={{
            "width": "90%",
            "display": "flex",
            "justifyContent": "flex-end",
          }}>
            <button style={{
              "backgroundColor": "white",
              "border": "none",
            }} onClick={props.handleLoginModal}>X</button>
          </div>
          <GoogleButton onClick={googleLogin} color={"#cf4332;"}>
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
  z-index : 10;

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