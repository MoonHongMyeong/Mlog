import React, { useState } from 'react'
import SingleComment from './SingleComment'
import { CommentLayout } from '../atoms/Layouts';
import { CommentTextarea } from '../atoms/Inputs';
import { LongButton } from '../atoms/Buttons';
import axios from 'axios';
import ReplyComment from './ReplyComment';


export default function Comments(props) {

  const [commentBody, setCommentBody] = useState("");
  const [valBody, setvalBody] = useState(false);


  const addComments = (e) => {
    const url = `/api/v2/posts/${props.postId}/comments`;
    const cmtData = {
      body: commentBody
    }
    return axios.post(url, cmtData);
  }

  const handleCommentChange = (e) => {
    setCommentBody(e.currentTarget.value);
    setvalBody(true);
  }

  const handleCommentSubmit = () => {
    if (valBody) {
      addComments()
        .then(response => {
          props.reRenderCommentsAdd(response.data);
          alert("댓글 등록이 완료되었습니다.");
          setCommentBody("");
        })
        .catch(error => console.log(error));
    } else {
      alert("댓글을 입력해주세요");
    }
  }
  return (
    <CommentLayout>
      <p style={{ "fontSize": "1.3rem", "fontWeight": "600" }}>
        {/* {commentsCount}개의 댓글 */}

      </p>
      {props.comments &&
        props.comments.map((comment, index) => {
          if (!comment.parents) {
            return (
              <>
                <SingleComment
                  reRenderCommentsAdd={props.reRenderCommentsAdd}
                  reRenderCommentsUpdate={props.reRenderCommentsUpdate}
                  comment={comment}
                  key={comment.id}
                  postId={props.postId}
                  SessionUser={props.SessionUser}
                />

                <ReplyComment
                  reRenderCommentsAdd={props.reRenderCommentsAdd}
                  reRenderCommentsUpdate={props.reRenderCommentsUpdate}
                  parentCommentId={comment.id}
                  comments={props.comments}
                  postId={props.postId}
                  SessionUser={props.SessionUser}
                />
              </>)
          } return <div></div>
        })}

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
        <CommentTextarea
          name="body"
          spellCheck="false"
          value={commentBody}
          onChange={handleCommentChange}
          placeholder={valBody ? "" : "댓글 내용을 입력해주세요."}
        />
        <LongButton
          style={{
            "width": "100%",
            "fontSize": "1rem",
            "borderRadius": "0",
          }}
          onClick={handleCommentSubmit}
        >
          댓글등록</LongButton>
      </div>

    </CommentLayout>
  )
}
