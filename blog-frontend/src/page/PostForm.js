import React from 'react';
import styled from 'styled-components';
import { PostLayout } from './components/atoms/Layouts';
import { TitleInput, FormTextarea } from './components/atoms/Inputs';
import { FormButton } from './components/atoms/Buttons';

export default function PostForm({ title, content }) {

  const windowHeight = (window.innerHeight - 300) + 'px';

  return (
    <>
      {console.log(windowHeight)}
      <PostLayout>
        <TitleInput placeholder="제목을 입력하세요." />
        <FormTextarea placeholder="내용을 입력하세요." height={windowHeight}></FormTextarea>
      </PostLayout>
      <FormTools>
        <div>
          <FormButton><i className="fas fa-arrow-left"></i> 나가기</FormButton>
        </div>
        <div>
          <FormButton>미리보기</FormButton>
          <FormButton>임시저장</FormButton>
          <FormButton>작성완료</FormButton>
        </div>
      </FormTools>
    </>
  )
}


const FormTools = styled.div`
width: 100%;
position: absolute;
height: 4rem;
bottom: 0;
display:flex;
justify-content: space-evenly;
align-items : center;

button {
  margin : 0.5rem;
}
`;