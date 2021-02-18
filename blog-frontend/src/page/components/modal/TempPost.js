import React, { useEffect } from 'react';
import styled from 'styled-components';
import { LongButton } from '../atoms/Buttons';
import { ModalBackLayout } from '../atoms/Layouts';
import TempCard from './TempCard';
const body = document.querySelector('#root');

export default function TempPost() {
  useEffect(() => {
    body.setAttribute("style", "overflow: hidden;");
    return () => {
      body.removeAttribute("style");
    }
  }, [])
  return (
    <ModalBackLayout>
      <ModalLayout>
        <p><span>임시 저장</span><button>X</button></p>

        <TempCard />

        <SubmitTempButton>현재 글 임시 저장</SubmitTempButton>
      </ModalLayout>
    </ModalBackLayout>
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
  border-radius : 10px;

  p{
    width : 90%;
    display :flex;
    justify-content : space-between;
  }

  p span{
    font-size : 1.3rem;
  }
  p button{
    background-color : #fafafa;
    border : none;
    font-size:1.3rem;

    &:hover{
      cursor:pointer;
    }
    &:focus{
      outline : none;
    }
  }

  @media screen and (max-width : 550px){
    width : calc(100% - 2rem);
  }

`;

const SubmitTempButton = styled(LongButton)`
width : 100%;
`;