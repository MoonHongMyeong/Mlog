import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { ModalBackLayout } from '../atoms/Layouts'
import axios from 'axios';
import CategoryCard from './CategoryCard';
import { Button } from '../atoms/Buttons';

const body = document.querySelector('#root');

export default function Category(props) {
  const [category, setCategory] = useState([]);
  const [categoryName, setcategoryName] = useState("");

  const onCategoryNameChange = e => {
    setcategoryName(e.currentTarget.value);
  }
  const submitCategories = () => {
    if (categoryName.length < 2) {
      alert("카테고리 이름을 적어주세요")
    } else {
      const data = {
        name: categoryName
      }
      axios.post(`/api/v2/user/${props.userId}/categories`, data)
        .then(response => {
          alert("카테고리가 추가 되었습니다");
          renderCategory();
        })
    }
  }

  const renderCategory = () => {
    axios.get(`/api/v2/user/${props.userId}/categories`)
      .then(response => {
        setCategory(response.data);
      })
  }

  useEffect(() => {
    body.setAttribute("style", "overflow: hidden;");
    axios.get(`/api/v2/user/${props.userId}/categories`)
      .then(response => {
        setCategory(response.data);
      })
    return () => {
      body.removeAttribute("style");
    }
  }, [props])
  return (
    <ModalBackLayout>
      <ModalLayout>
        <p>
          <span>카테고리 목록</span>
          <button onClick={props.handleCategories}>X</button>
        </p>
        <div style={{
          "margin": "0 auto",
          "width": "100%"
        }}>
          {
            category &&
            category.map((cate, index) => {
              return <CategoryCard renderCategory={renderCategory}
                key={index}
                userId={props.userId}
                category={cate} />
            })

          }
        </div>
        <div style={{
          "display": "flex",
          "flexDirection": "column"

        }}>
          <input type="text"
            id="input"
            name="name"
            value={categoryName}
            onChange={onCategoryNameChange}
            placeholder="추가할 카테고리 이름을 넣어주세요"
            style={{ "width": "100%", "border": "none", "height": "2rem" }}
          />
          <Button style={{ "width": "100%" }} onClick={submitCategories}>추가</Button>
        </div>

      </ModalLayout>
    </ModalBackLayout>
  )
}

const ModalLayout = styled.div`
  width : 25rem;
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

  @media screen and (max-width:500px){
    width : 80%;    
  }
`;
