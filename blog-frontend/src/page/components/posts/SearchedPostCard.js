import styled from 'styled-components';
import React from 'react';
import { Link } from 'react-router-dom';

export default function SearchedPostCard(props) {
  return (
    <>
      <CardContainer>
        <CardImg>
          <Link to={`/api/v2/posts/${props.post.id}`}>
            <img src={props.post.imageUrl} alt={props.post.title} />
          </Link></CardImg>
        <div id="postInfo">
          <Link to={`/api/v2/posts/${props.post.id}`}>
            <CardTitle><span>{props.post.title}</span></CardTitle>
          </Link>
          <CardAuthor>
            <Link to={`/api/v2/user/${props.post.user.id}`}>
              <div id="author">
                <div id="profile">
                  <img src={props.post.user.picture}
                    alt={props.post.user.name}
                  />
                </div>
                <div id="userInfo">
                  <span>{props.post.user.name}</span>
                  <span style={{ "fontSize": "0.4rem" }}>{props.post?.modifiedDate.substr(0, 10)}</span>
                </div>
              </div>
            </Link>
          </CardAuthor>
        </div>
      </CardContainer>
    </>
  )
}

const CardContainer = styled.div`
  width : 340px;
  margin : 2rem 1.25rem;
  transition : box-shadow 0.25s ease-in-out, transform 0.25s ease-in 0s;
  &:hover {
    box-shadow : 0px 4px 16px 0px #3d3d3d;
    transform : translateY(-.5rem);
  }

  #postInfo a{
  text-decoration : none;
  }
  @media screen and (max-width:1200px){
    width : 288px;
    margin : 1.5rem 1rem;
  }
  @media screen and (max-width:990px){
    display : flex;
    flex-direction : row-reverse;
    width : calc(100% - 5rem);
    margin : 1rem auto;
    justify-content:space-between;
    #postInfo{
      width : 70%;
      height : 100%;      
    }
  }
  @media screen and (max-width:768px){
    width : 99% auto;
    #postInfo{
      width : 80%;
      height : 100%;
    }
  }
`;

const CardImg = styled.div`
  width : 100%;
  height : 15.625rem;
  overflow:hidden;
  img {
    width : 100%;
    height : 100%;
  }
  @media screen and (max-width:990px){
    width : 9.375rem;
    height : 9.375rem;
    img{
      width : 200%;
      height : 200%;
      margin : -50%;
    }
  }
`;

const CardTitle = styled.div`
  width : 100%;
  font-size : 1.3rem;
  font-weight : 700;
  color : black;
  
  span{
  text-decoration:none;
  margin-left : .4rem;
  }

  @media screen and (max-width:990px){
    height : 4.6875rem;
    display:flex;

    *{
      margin : 2.3rem 1rem;
    }
  }
  @media screen and (max-width:500px){
    height : 100px;
    margin-top : -1rem;
    font-size : 1rem;
  }

`;
const CardAuthor = styled.div`
  width : 100%;
  display : flex;
  justify-content :space-between;
  align-items:center;
  overflow : hidden;

  a{
    text-decoration:none;
    color:black;
  }

  #author{
    display : flex;
    align-items : center;
    font-size : .8rem;
  }

  #profile{
    width : 2rem;
    height : 2rem;
    border-radius:2rem;
    background-color : hotpink;
    overflow: hidden;
    margin-right : 0.3rem;
  }

  #profile img{
    width : 100%;
    height : 100%;
  }

  #userInfo{
    display : flex;
    flex-direction:column;
  }
  #author:hover{
    text-decoration:underline;
  }

  @media screen and (max-width:990px){
    height : 4.6875rem;
    #author{
      margin-left : .5rem;
      margin-bottom : 2rem;
    }
  }

  @media screen and (max-width : 500px)  {
    height : 66px;
    
    justify-content : flex-start;
    
    #author {
      margin-bottom : 0;
    }
    
    #profile {
      width : 1.5rem;
      height : 1.5rem;
    }
    span{
      font-size : 0.3rem;
    }
  }
`;