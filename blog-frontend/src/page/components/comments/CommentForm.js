import React from 'react'
import { CommentTextarea } from '../atoms/Inputs';
import { LongButton } from '../atoms/Buttons';
export default function CommentForm() {
  return (
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
  )
}
