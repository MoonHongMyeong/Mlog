import React, { useState } from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { CommentTextarea } from '../atoms/Inputs';
import { CommentButton, LongButton } from '../atoms/Buttons';

export default function SingleComment() {

  const [modifyMode, setModifyMode] = useState(false);
  const [onReplyTo, setOnReplyTo] = useState(false);

  const handleModifyMode = () => {
    setModifyMode(!modifyMode);
  }

  const handleReplyTo = () => {
    setOnReplyTo(!onReplyTo);
  }

  return (
    <>
      {modifyMode ?
        <div className="commentForm"
          style={{
            "width": "99%",
            "display": "flex",
            "flexDirection": "column",
            "alignItems": "center",
            "justifyContent": "center",
            "margin": "2rem auto",
            "boxShadow": "0 2px 6px 0px"
          }}>
          <CommentTextarea />
          <div style={{
            "width": "100%",
            "display": "flex",
          }}>
            <LongButton
              style={{
                "width": "50%",
                "fontSize": "1rem",
                "borderRadius": "0",
              }}
            >수정완료</LongButton>
            <LongButton
              style={{
                "width": "50%",
                "fontSize": "1rem",
                "borderRadius": "0",
              }}
              onClick={handleModifyMode}
            >수정취소</LongButton>
          </div>
        </div>
        :
        <CommentCard>
          <CardAuthor>
            <Link to="/">
              <div id="author">
                <div id="profile"><img src="" alt="?" /></div>
                <div id="userInfo">
                  <span style={{ "fontSize": "1rem" }}>comment.user.name</span>
                  <span style={{ "fontSize": "0.5rem" }}>modifiedDate</span>
                </div>
              </div>
            </Link>
            <CommentTools>
              <CommentButton onClick={setModifyMode}>수정</CommentButton>
              <CommentButton>삭제</CommentButton>
              <CommentButton onClick={handleReplyTo}>ReplyTo</CommentButton>
            </CommentTools>
          </CardAuthor>
          <div className="commentBody"
            style={{
              "margin": "1.5rem 0.5rem"
              , "wordBreak": "break-all"
            }}>
            asdfasfsdf
          </div>
        </CommentCard>
      }
      {onReplyTo &&
        <div className="commentForm"
          style={{
            "width": "99%",
            "display": "flex",
            "flexDirection": "column",
            "alignItems": "center",
            "justifyContent": "center",
            "margin": "1rem auto",
            "boxShadow": "0 2px 6px 0px"
          }}>
          <CommentTextarea />
          <LongButton
            style={{
              "width": "100%",
              "fontSize": "1rem",
              "borderRadius": "0",
            }}
          >
            댓글등록</LongButton>
        </div>
      }
    </>
  )
}

const CommentCard = styled.div`
  width : 99%;
  margin : 0 auto;
  box-shadow : 0px 2px 6px 0px;
  margin-bottom : 1.5rem;
`;
const CardAuthor = styled.div`
  width : 98%;
  margin : .5rem .5rem;
  display : flex;
  flex-direction : row;
  justify-content :space-between;
  align-items:center;

  a{
    text-decoration:none;
    color:black;
  }

  #author{
    display : flex;
    align-items : center;
    font-size : .8rem;
  }

  #profile{
    width : 3rem;
    height : 3rem;
    border-radius: 3rem;
    background-color : deepskyblue;
  }

  #userInfo{
    display : flex;
    flex-direction:column;
  }
  #author:hover{
    text-decoration:underline;
  }
`;

const CommentTools = styled.div`
  width : 90%;
  font-size : 0.9rem;
  display : flex;
  justify-content:flex-start;
  margin-left : 1rem;
`;