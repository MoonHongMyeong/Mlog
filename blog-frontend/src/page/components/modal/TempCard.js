import React, { useEffect } from 'react';
import styled from 'styled-components';
import axios from 'axios';
export default function TempCard(props) {

  useEffect(() => {
    props.reRenderTempPostDelete();
  }, [props])

  const deleteTempPost = () => {
    return axios.delete(`/api/v2/posts/${props.temp.id}`);
  }

  const handleDeleteTempPost = () => {
    deleteTempPost()
      .then(response => {
        alert("임시 저장글이 삭제되었습니다.");
        props.reRenderTempPostDelete();
      })
      .catch(error => console.log(error));
  }

  return (
    <CardLayout>
      <h3 style={{
        "paddingLeft": "1rem",
        "wordBreak": "break-all"
      }}
        onClick={props.loadTempPosts({
          title: props.temp.title,
          content: props.temp.content
        })}
      >{props.temp.title}</h3>
      <XButton onClick={handleDeleteTempPost}>X</XButton>
    </CardLayout>
  )
}

const CardLayout = styled.div`
  width : 80%;
  margin : 0.2rem;
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
