import React, { useState } from 'react'
import styled from 'styled-components';
import axios from 'axios';
import SingleComment from './SingleComment';
import ReplyComment from './ReplyComment';

export default function Comment(props) {

  const [commentAuthor, setCommentAuthor] = useState("");
  const [commentBody, setCommentBody] = useState("");

  const handleAuthorChange = (e) => {
    setCommentAuthor(e.currentTarget.value);
  }

  const handleBodyChange = (e) => {
    setCommentBody(e.currentTarget.value);
  }

  const commentFormSubmit = (e) => {
    e.preventDefault();
    const url = `/api/posts/${props.postId}/comments`
    const commentInfo = {
      author: commentAuthor,
      body: commentBody
    }
    axios.post(url, commentInfo)
      .then(response => props.reRenderComments(response.data))
      .then(alert("댓글 등록이 성공했습니다.")).then(setCommentAuthor(""), setCommentBody(""))
      .catch(error => console.log(error))
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
                    name="author" /></td>
              </tr>
              <tr>
                <td>
                  <textarea
                    name="body"
                    id="comment"
                    value={commentBody}
                    onChange={handleBodyChange}
                    placeholder="댓글을 작성해주세요"></textarea>
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
      {props.commentsList && props.commentsList.map((comment, index) => {
        if (!comment.parents) {
          return (
            <>
              <SingleComment reRenderComments={props.reRenderComments} comment={comment} key={comment.id} postId={props.postId} />
              <ReplyComment reRenderComments={props.reRenderComments} parentCommentId={comment.id} key={index} commentsList={props.commentsList} postId={props.postId} />
            </>)
        }

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