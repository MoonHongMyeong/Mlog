import React from 'react'

export default function UpdatePostForm() {
  return (
    <div>
      <form >
        <div className="author">
          <div className="picture"></div>
          <div className="user">
            <h2>{post.author}</h2>
            <p>{post.author}</p>
          </div>
        </div>
        <div>
          <input type='text' name="title" onChange={handleValueChange}>제목</input>
        </div>
        <div id="markdown_content">
          <textarea name="content" onChange={handleValueChange}>{post.content}</textarea>
        </div>
        <button type="submit">수정완료</button>
        <button >수정취소</button>
      </form>
    </div>
  )
}
