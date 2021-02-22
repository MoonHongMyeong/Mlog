import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { LongButton } from '../atoms/Buttons';
import { ModalBackLayout } from '../atoms/Layouts';
import TempCard from './TempCard';
import axios from 'axios';

const body = document.querySelector('#root');

export default function TempPost(props) {

  const [Data, setData] = useState({});
  const [tempPosts, setTempPosts] = useState([]);

  useEffect(() => {
    console.log(props);
    body.setAttribute("style", "overflow: hidden;");
    axios.get("/write/temp").then(response => setTempPosts(response));
    return () => {
      body.removeAttribute("style");
    }
  }, [])

  const addTempPost = () => {
    const url = "write/temp";
    setData({
      title: props.title,
      content: props.content
    })
    return axios.post(url, Data);
  }

  const handleTempPostSubmit = (e) => {
    if (props.title && props.content) {
      addTempPost()
        .then(response => {
          alert("현재 글이 임시저장 되었습니다.");
          setTempPosts(tempPosts.concat(response));
        })
        .catch(error => console.log(error));
    } else {
      alert("제목과 내용을 입력해야 임시저장이 가능합니다.");
    }
  }
  return (
    <ModalBackLayout>
      <TempModalLayout>
        <p><span>임시 저장</span><button onClick={props.onModalTempPost}>X</button></p>
        {tempPosts ?
          tempPosts.map(temp => {
            return <TempCard temp={temp} />
          })
          : <h3>임시 저장글이 없습니다.</h3>}


        <SubmitTempButton onClick={handleTempPostSubmit}>현재 글 임시 저장</SubmitTempButton>
      </TempModalLayout>
    </ModalBackLayout>
  )
}
const TempModalLayout = styled.div`
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