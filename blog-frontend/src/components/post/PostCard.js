import React from 'react'

function PostCard({ posts }) {
  return (
    <>
      {posts.map(post => {
        return (
          <div className="post" key={post.id}>
            <div className="postImg"><img src={post.imageUrl} alt={post.title} /></div>
            <div className="postTitle">
              <h2><a href={`/api/posts/${post.id}`}>{post.title}</a></h2>
              <div className="profile">
                <div className="picture"></div>
                <div className="user">
                  <span className="name">{post.author}</span>
                  <span className="date">{post.modifiedDate}</span>
                </div>
              </div>
            </div>
          </div>)
      })
      }
    </>
  )
}

export default PostCard;