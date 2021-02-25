import React from 'react';
import styled from 'styled-components';
import axios from 'axios';
export default function CategoryCard(props) {
  const deleteCategory = () => {
    return axios.delete(`/api/v2/user/${props.userId}/categories/${props.category.id}`);
  }

  const handleDeleteCategory = () => {
    deleteCategory()
      .then(response => {
        alert("카테고리가 삭제되었습니다.");
        props.renderCategory();
      })
      .catch(error => console.log(error));
  }

  return (
    <CardLayout>
      <h3 style={{
        "paddingLeft": "1rem",
        "wordBreak": "break-all",
      }}>{props.category.name}</h3>
      <XButton onClick={handleDeleteCategory}>X</XButton>
    </CardLayout>
  )
}

const CardLayout = styled.div`
  width : 90%;
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
