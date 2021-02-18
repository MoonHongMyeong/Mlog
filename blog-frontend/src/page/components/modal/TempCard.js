import React from 'react';
import styled from 'styled-components';
export default function TempCard() {
  return (
    <CardLayout>
      <h3 style={{
        "paddingLeft": "1rem",
        "wordBreak": "break-all"
      }}>post.title</h3>
      <XButton>X</XButton>
    </CardLayout>
  )
}

const CardLayout = styled.div`
  width : 80%;
  margin : 1.5rem;
  height : 5rem;
  box-shadow : 0px 2px 10px 0px;
  display : flex;
  flex-direction : row;
  align-items : center;
  justify-content : space-between;
`;

const XButton = styled.button`
  margin-right : 0.5rem;
  font-size : 1rem;
  font-weight: 800;
  background-color : #fafafa;
  border : none;
  &:hover{
    cursor : pointer;
  }
  &:focus{
    outline : none;
  }
`;
