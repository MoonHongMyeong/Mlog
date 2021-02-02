import React, { useState } from 'react'
import styled from 'styled-components';
import axios from 'axios';
import SingleComment from './SingleComment';
import ReplyComment from './ReplyComment';

export default function Comment(props) {

  const [commentAuthor, setCommentAuthor] = useState("");
  const [commentBody, setCommentBody] = useState("");
  const [valAuthor, setvalAuthor] = useState(false);
  const [valBody, setvalBody] = useState(false);

  const handleAuthorChange = (e) => {
    setCommentAuthor(e.currentTarget.value);
    setvalAuthor(true);
  }

  const handleBodyChange = (e) => {
    setCommentBody(e.currentTarget.value);
    setvalBody(true);
  }

  const commentFormSubmit = (e) => {
    e.preventDefault();
    if (valAuthor && valBody) {
      const url = `/api/posts/${props.postId}/comments`
      const commentInfo = {
        author: commentAuthor,
        body: commentBody
      }
      axios.post(url, commentInfo)
        .then(response => props.reRenderCommentsAdd(response.data))
        .then(alert("댓글 등록이 성공했습니다.")).then(setCommentAuthor(""), setCommentBody(""), setvalAuthor(false), setvalBody(false))
        .catch(error => console.log(error))
    } else {
      alert("댓글을 입력해주세요")
    }
  }


  return (
    <div>
      {/* comment form */}
      <CommentFormContainer>
        <form onSubmit={commentFormSubmit}>
          <table>
            <tbody>
              <tr>
                <td><label htmlFor="author">작성자 : </label>
                  <input
                    type="text"
                    value={commentAuthor}
                    onChange={handleAuthorChange}
                    placeholder={!valAuthor && "작성자를 입력해주세요."}
                    name="author" /></td>
              </tr>
              <tr>
                <td>
                  <textarea
                    name="body"
                    id="comment"
                    value={commentBody}
                    onChange={handleBodyChange}
                    placeholder={!valBody && "댓글내용을 입력해주세요."}
                  ></textarea>
                </td>
              </tr>
              <tr>
                <td>
                  <button type="submit">댓글등록</button>
                </td>
              </tr>
            </tbody>
          </table>
        </form>
      </CommentFormContainer>
      {/* comment List */}
      {props.commentsList && props.commentsList.map((comment) => {
        if (!comment.parents) {
          return (
            <>
              <SingleComment
                reRenderCommentsAdd={props.reRenderCommentsAdd}
                reRenderCommentUpdate={props.reRenderCommentUpdate}
                comment={comment}
                postId={props.postId} />

              <ReplyComment
                reRenderCommentsAdd={props.reRenderCommentsAdd}
                reRenderCommentUpdate={props.reRenderCommentUpdate}
                parentCommentId={comment.id}
                commentsList={props.commentsList} postId={props.postId} />
            </>)
        } return <div></div>

      })}

    </div>
  )
}

const CommentFormContainer = styled.div`
  margin : 0 auto;
  width : 45vw;
  padding : 10px 10px;
  border-radius : 5px;
  border : 1px solid #dedede;
  box-shadow : 2px 4px 3px #222f3e;
  margin-top : 1rem;
  margin-bottom : 1rem;

  table{
    width : 100%;
    height : 100%;
  }
  input{
    border-color: #dedede;
  }
  input:focus{
    outline : none;
  }
  textarea {
    overflow-x: hidden;
    resize: none;
    border-color: #dedede;
    border-radius: 5px;
    width: 100%;
    height: 6rem;
  }
  textarea::placeholder {
    padding-left: 10px;
    padding-top: 10px;
    font-size: 15px;
  }
  .cmt_tool .cmtBtn {
    background-color: white;
    font-size: 16px;
    padding: 5px 5px;
    border-radius: 5px;
    border-color: #dedede;
  }
  .cmt_tool .cmtBtn:hover {
    background-color: black;
    color: white;
    cursor: pointer;
  }
  @media screen and (max-width:1024px){
    width : 75vw;
  }
  .cmt textarea {
    width : 90%;
  }
`;