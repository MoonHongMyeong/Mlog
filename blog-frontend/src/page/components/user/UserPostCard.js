import React from 'react'
import styled from 'styled-components';
import { Link } from 'react-router-dom';
export default function UserPost() {
  return (
    <CardLayout>
      <Link to="/user/posts" className="userPostCard">
        <div id="img">
          <img src="/images/default.png" alt="?"></img>
        </div>
        <h2 style={{
          "textDecoration": "none",
          "color": "black",
        }}>postTitle</h2>
        <span style={{
          "color": "grey",
        }}>post.content 한줄 34 한 80자 받으면sadadfasdfsafdasdfsdfsafsdfadfasdfasdfasfasdfsadfaf 되겠다...</span>
        <div style={{
          "display": "flex",
          "justifyContent": "space-between",
          "fontSize": "0.8rem",
        }}>
          <p style={{
            "color": "black"
          }}>modifiedDate</p>
          <p style={{
            "color": "grey"
          }}><i className="far fa-thumbs-up"></i>12</p>
        </div>
      </Link>
    </CardLayout>
  )
}

const CardLayout = styled.div`
  width : 70%;
  height : 30rem;
  margin : 2rem auto;
  border-bottom : 1px solid grey;
  word-break : break-all;
  overflow : hidden;
  .userPostCard{
    text-decoration:none;
  }

  #img{
    width : 100%;
    height : 65%;
    overflow : hidden;
  }

  #img img{
    width : 100%;
    height : 100%;
  }

  @media screen and (max-width : 768px){
    height : 25rem;

    #img{
      width : 100%;
      height : 50%;
    }
  }
`;

