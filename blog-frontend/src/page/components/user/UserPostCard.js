import React from 'react'
import styled from 'styled-components';

export default function UserPostCard(props) {
  return (
    <CardLayout>
      <div id="img">
        <img src={props.post.imageUrl} alt={props.post.title}></img>
      </div>
      <h2 style={{
        "textDecoration": "none",
        "color": "black",
      }}>{props.post.title}</h2>
      <span style={{
        "color": "grey",
      }}>{props.post.content}</span>
      <div style={{
        "display": "flex",
        "justifyContent": "space-between",
        "fontSize": "0.8rem",
      }}>
        <p style={{
          "color": "black"
        }}>{props.post.modifiedDate.substr(0, 10)}</p>
        <p style={{
          "color": "grey"
        }}><i className="far fa-thumbs-up"></i>{props.post.likeCount}</p>
      </div>
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

