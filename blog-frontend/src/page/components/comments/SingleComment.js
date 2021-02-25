import React, { useState } from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { CommentTextarea } from '../atoms/Inputs';
import { CommentButton, LongButton } from '../atoms/Buttons';
import axios from 'axios';

export default function SingleComment(props) {

  const [modifyMode, setModifyMode] = useState(false);
  const [onReplyTo, setOnReplyTo] = useState(false);
  const [ReplyBody, setReplyBody] = useState("");
  const [EditCommentBody, setEditCommentBody] = useState(props.comment.body);
  const [valReplyBody, setvalReplyBody] = useState(false);

  const handleModifyMode = () => {
    setModifyMode(!modifyMode);
  }

  const handleReplyTo = () => {
    setOnReplyTo(!onReplyTo);
  }

  const handleCommentBodyChange = (e) => {
    setEditCommentBody(e.currentTarget.value);
  }

  const handleReplyBodyChange = (e) => {
    setReplyBody(e.currentTarget.value);
    setvalReplyBody(true);
  }

  const url = `/api/v2/posts/${props.postId}/comments/${props.comment.id}`;

  const submitReply = () => {
    if (valReplyBody && ReplyBody !== '') {
      const replyInfo = {
        body: ReplyBody
      }
      axios.post(url, replyInfo)
        .then(response => {
          props.reRenderCommentsAdd(response.data);
          alert("댓글 등록 성공");
          setReplyBody("");
          handleReplyTo();
        })
        .catch(error => console.log(error));
    } else {
      alert("댓글을 입력해주세요.")
    }
  }

  const submitEditComment = () => {
    const exceptedPost = {
      body: EditCommentBody
    }

    axios.put(url, exceptedPost)
      .then(response => {
        alert("댓글이 수정되었습니다.");
        props.reRenderCommentsUpdate();
        handleModifyMode();
      }).catch(error => console.log(error));
  }

  const deleteComment = () => {
    if (window.confirm("댓글을 삭제하시겠습니까?")) {
      axios.delete(url)
        .then(response => {
          props.reRenderCommentsUpdate();
          alert("댓글을 삭제했습니다.")
        }).catch(error => console.log(error));
    }
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
            "boxShadow": "0 2px 6px 0px",
          }}>
          <CommentTextarea
            onChange={handleCommentBodyChange}
            value={EditCommentBody}
            defaultValue={props.comment.body}
          />
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
              onClick={submitEditComment}
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
                <div id="profile"><img src={props.comment.user.picture} alt={props.comment.user.name} /></div>
                <div id="userInfo">
                  <span style={{ "fontSize": "1rem" }}>{props.comment.user.name}</span>
                  <span style={{ "fontSize": "0.5rem" }}>{props.comment.modifiedDate.substr(0, 10)}</span>
                </div>
              </div>
            </Link>
            <CommentTools>
              {props.comment.user.id === parseInt(props.SessionUser?.id) &&
                <>
                  <CommentButton onClick={setModifyMode}>수정</CommentButton>
                  <CommentButton onClick={deleteComment}>삭제</CommentButton>
                </>
              }
              <CommentButton onClick={handleReplyTo}>ReplyTo</CommentButton>
            </CommentTools>
          </CardAuthor>
          <div className="commentBody"
            style={{
              "margin": "1.5rem 0.5rem"
              , "wordBreak": "break-all"
            }}>
            {props.comment.parents &&
              <>
                <span style={{
                  "backgroundColor": "#ffeaa7"

                }}>@{props.comment.parents.user.name}</span><br />
              </>}
            {props.comment.body}
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
          <CommentTextarea
            name="body"
            spellCheck="false"
            value={ReplyBody}
            onChange={handleReplyBodyChange}
            placeholder={!valReplyBody ? "댓글을 작성해주세요" : ""}
          />
          <LongButton
            style={{
              "width": "100%",
              "fontSize": "1rem",
              "borderRadius": "0",
            }}
            onClick={submitReply}
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
    overflow : hidden;
  }

  #profile img {
            width : 100%;
    height : 100%;
  }

  #userInfo{
    margin-left : 0.3rem;
    width : 4rem;
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