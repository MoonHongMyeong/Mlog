import React, { useEffect } from 'react';
import styled from 'styled-components';
import { LongButton } from '../atoms/Buttons';
import { ModalBackLayout } from '../atoms/Layouts'

const body = document.querySelector('#root');

export default function Category() {
  useEffect(() => {
    body.setAttribute("style", "overflow: hidden;");
    return () => {
      body.removeAttribute("style");
    }
  }, [])
  return (
    <ModalBackLayout>
      <ModalLayout>
        <p><span>게시글 발행</span><button>X</button></p>
        <div>
          <label HtmlFor="thumbnail">썸네일</label>
          <input type="file" id="thumbnail" />
        </div>
        <div>
          <label htmlFor="category">시리즈</label>
          <select name="category" id="category">
            <option value="">선택하지 않음</option>
          </select>
        </div>
        <SubmitButton>글을 발행합니다.</SubmitButton>
      </ModalLayout>
    </ModalBackLayout>
  )
}

const ModalLayout = styled.div`
  width : 30rem;
  padding : 1rem;
  background-color : #fafafa;
  display : flex;
  flex-direction : column;
  border-radius : 10px;
  box-shadow : 0px 4px 6px 0px;

  p{
    display : flex;
    justify-content : space-between;
  }

  p span{
    font-size : 1.5rem;
    font-weight : 600;
  }

  p button{
    background-color : #fafafa;
    border : none;
    font-size:1.5rem;

    &:hover{
      cursor:pointer;
    }
    &:focus{
      outline : none;
    }
  }

  div{
    margin : 1rem 1rem;
    overflow:hidden;
  }  

  div label {
    padding : 0.5rem;
    background-color : black;
    color : white;
    margin-right : 0.5rem;
  }
  div input[type="file"] {
    width : 83.5%;
    height : 2rem;
  }

  #file-upload-button{
    height : 2rem;
  }
  
  div select {
    width : 83.5%;
    height : 2rem;
  }
`;

const SubmitButton = styled(LongButton)`
  width : 100%;
`;