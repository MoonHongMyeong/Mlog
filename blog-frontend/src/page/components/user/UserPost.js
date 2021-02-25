import React, { useState, useEffect } from 'react'
import UserPostCard from './UserPostCard';
import axios from 'axios';
import Loading from '../common/Loading';

export default function UserPost(props) {
  const [posts, setPosts] = useState([]);
  const [isLoading, setisLoading] = useState(true);
  useEffect(() => {

    axios.get(props.location.pathname)
      .then(response => {
        setPosts(response.data)
      })
      .catch(error => console.log(error))
      .then(setisLoading(false));
  }, [props.location.pathname])

  return (
    <>
      {isLoading ? <Loading /> :
        <>
          {
            posts.length === 0 ?
              <div style={{
                "textAlign": "center",
                "marginTop": "2rem"
              }}>포스트가 존재하지 않습니다.</div>
              :
              posts.map((post) => {
                return (
                  <>
                    <a style={{ "textDecoration": "none" }} href={`/api/v2/posts/${post.id}`}>
                      <UserPostCard post={post} key={post.id} />
                    </a></>)
              })
          }
        </>
      }
    </>
  )
}

