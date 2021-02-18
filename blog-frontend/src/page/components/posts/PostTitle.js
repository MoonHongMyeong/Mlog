import React from 'react'

export default function PostTitle() {
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
      }}>post.category</span>
      <span style={{
        "fontSize": "1.8rem",
        "fontWeight": "700",
        "wordBreak": "break-all"
      }}>post.title // asdfjjasdklfjas d;lfjaskjglskgjl;sdjgl;skdjglsadasds</span>
      <span style={{
        "fontSize": "0.8rem",
        "color": "grey"
      }}>2021.02.17T15:26:00 (updated 2021.02.17T16:00:00)</span>
    </div>
  )
}
