import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { LongButton } from '../atoms/Buttons';
import { ModalBackLayout } from '../atoms/Layouts'
import axios from 'axios';

const body = document.querySelector('#root');

export default function Category(props) {

  const [image, setImage] = useState({
    file: null,
    fileName: ""
  });
  const [category, setCategory] = useState("");
  const [userCategories, setUserCategories] = useState([]);


  const handleFileChange = (e) => {
    setImage({
      file: e.target.files[0],
      fileName: e.target.value
    })
  }
  const handleCategoryChange = (e) => {
    setCategory(e.target.value)
  }

  const addPost = () => {
    const url = '/api/v2/write';
    const formData = new FormData();
    formData.append('title', props.title);
    formData.append('content', props.content);
    formData.append('image', image);
    formData.append('categories', category);
    const config = {
      headers: {
        'Content-type': 'multipart/form-data'
      }
    }

    return axios.post(url, formData, config);
  }

  const handleFormSubmit = (e) => {
    addPost().then((response) => {
      alert('게시글 등록이 완료되었습니다.');
      console.log(response);
      props.history.push(`/api/v2/posts/${response.data}`)
    }).catch(error => console.log(error));
  }

  useEffect(() => {
    body.setAttribute("style", "overflow: hidden;");
    //유저아이디를 어디서 어떻게 받아와야할지....
    // const url = `/api/v2/user/${props.user.id}/categories`;
    // axios.get(url)
    //   .then(response => setUserCategories(response))
    //   .catch(error => console.log(error));
    return () => {
      body.removeAttribute("style");
    }
  }, [])
  return (
    <ModalBackLayout>
      {console.log(props)}
      <ModalLayout>
        <p>
          <span>게시글 발행</span>
          <button onClick={props.onModalCategory}>X</button>
        </p>
        <div>
          <label htmlFor="thumbnail">썸네일</label>
          <input onChange={handleFileChange}
            image={image.file}
            value={image.fileName}
            accept="image/*"
            name="image"
            type="file"
            id="thumbnail" />
        </div>
        <div>
          <label htmlFor="category">시리즈</label>
          <select
            value={category}
            onChange={handleCategoryChange}
            name="category"
            id="category">
            <option value="">선택하지 않음</option>
            {userCategories &&
              userCategories.map(category => {
                return <option value={category}>{category}</option>
              })}
          </select>
        </div>
        <SubmitButton onClick={handleFormSubmit}>글을 발행합니다.</SubmitButton>
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