import React from 'react'
import styled from 'styled-components';
import { Link } from 'react-router-dom';
export default function UserCategoryCard() {
  return (
    <CardLayout>
      <Link style={{ "textDecoration": "none", "backgroundColor": "transparent" }} to="/user/categories">
        <h2 style={{
          "zIndex": "1",
          "left": "41%",
          "top": "100%",
          "display": "flex",
          "flexDirection": "column",
          "alignItems": "center",
          "color": "black",
        }}>
          <span>
            <i className="fas fa-chevron-left"></i>
          category.name 시리즈
          <i className="fas fa-chevron-right"></i>
          </span>
          <span style={{
            "fontSize": "1.2rem",
          }}>modifiedDate</span>
        </h2>
      </Link>
    </CardLayout>

  )
}


const CardLayout = styled.div`
  width : 100%;
  height : 300px;
  overflow: hidden;
  background-color : white;
  border-bottom : 1px solid #000000;
  display : flex;
  flex-direction : column;
  justify-content : center;
  align-items : center;
  opacity : 0.5;
  
  &:hover{
    opacity : 1;
    text-decoration : underline;
  }
`;


