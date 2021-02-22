import React from 'react'

export default function PostTitle(props) {
  return (
    <div style={{
      "display": "flex",
      "flexDirection": "column",
      "flexWrap": "wrap"
    }}>
      <span style={{
        "fontSize": "1.2rem",
        "fontWeight": "500",
        "wordBreak": "break-all"
      }}>{props.posts.categories ? props.posts.categories : "일반"}</span>
      <span style={{
        "fontSize": "1.8rem",
        "fontWeight": "700",
        "wordBreak": "break-all"
      }}>{props.posts.title}</span>
      <span style={{
        "fontSize": "0.8rem",
        "color": "grey"
      }}>{props.posts.createdDate} (updated : {props.posts.modifiedDate})</span>
    </div>
  )
}
