import React from 'react'
import SingleComment from './SingleComment'
import { CommentLayout } from '../atoms/Layouts';
import { CommentTextarea } from '../atoms/Inputs';
import { LongButton } from '../atoms/Buttons';

export default function Comments() {
  return (
    <CommentLayout>
      <p style={{ "fontSize": "1.3rem", "fontWeight": "600" }}>
        00개의 댓글</p>

      <SingleComment />
      <SingleComment />
      <SingleComment />
      <SingleComment />
      <SingleComment />
      <SingleComment />

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
        <LongButton
          style={{
            "width": "100%",
            "fontSize": "1rem",
            "borderRadius": "0",
          }}
        >
          댓글등록</LongButton>
      </div>

    </CommentLayout>
  )
}
