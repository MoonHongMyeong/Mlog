import React, { useState, useEffect } from 'react'
import { Link } from 'react-router-dom';

function PostCard({ posts }) {
  const [width, setWidth] = useState(window.innerWidth);
  useEffect(() => {
    setWidth(window.innerWidth);
  }, [])
  return (
    <>
      {
        Array.from(posts).map(post => {
          return (
            <div className="post" key={post.id}>
              <div className="postImg"><img src={post.imageUrl} alt={post.title} /></div>
              <div className="postTitle">
                {width < 550 ? <h2><Link to={`/api/posts/${post.id}`}>{post.title.substr(0, 8)}...</Link></h2>
                  :
                  <h2><Link to={`/api/posts/${post.id}`}>{post.title}</Link></h2>
                }

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