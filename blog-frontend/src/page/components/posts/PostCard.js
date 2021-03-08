import styled from 'styled-components';
import React from 'react';
import { Link } from 'react-router-dom';

export default function PostCard(props) {
  let myRefs = [];
  const saveCardRef = (ref) => {
    myRefs.push(ref);
  };

  return (
    <>
      {Array.from(props.post).map((post, index) => {
        return (
          <CardContainer
            ref={saveCardRef}
            key={index}
            onMouseEnter={() => {
              myRefs[index].setAttribute("style", "box-shadow : 0px 4px 16px 0px #3d3d3d;transform : translateY(-.5rem)")
            }}
            onMouseLeave={() => {
              myRefs[index].removeAttribute("style")
            }}
            onTouchStart={() => {
              myRefs[index]
                .setAttribute("style", "box-shadow : 0px 4px 16px 0px #3d3d3d;transform : translateY(-.5rem)")
            }}
            onTouchEnd={() => {
              myRefs[index].removeAttribute("style")
            }}
          >
            <CardImg>
              <Link to={`/api/v2/posts/${post.id}`}>
                <img src={post.imageUrl} alt={post.title} />
              </Link></CardImg>
            <div id="postInfo">
              <Link to={`/api/v2/posts/${post.id}`}>
                <CardTitle><span>{post.title}</span></CardTitle>
              </Link>
              <CardAuthor>
                <Link to={`/api/v2/user/${post.user.id}`}>
                  <div id="author">
                    <div id="profile">
                      <img src={post.user.picture}
                        alt={post.user.name}
                      />
                    </div>
                    <div id="userInfo">
                      <span>{post.user.name}</span>
                      <span style={{ "fontSize": "0.4rem" }}>{post.modifiedDate.substr(0, 10)}</span>
                    </div>
                  </div>
                </Link>
                <div id="like">
                  <span style={{ "color": "grey" }}>
                    <i className="fas fa-thumbs-up"></i><span> </span>
                    {post.likeCount}
                  </span>
                </div>
              </CardAuthor>
            </div>
          </CardContainer>
        )
      })}

    </>
  )
}

const CardContainer = styled.div`
  width : 340px;
  margin : 2rem 1.25rem;
  transition : box-shadow 0.25s ease-in-out, transform 0.25s ease-in 0s;
  /* &:hover {
    box-shadow : 0px 4px 16px 0px #3d3d3d;
    transform : translateY(-.5rem);
  } */

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
    #like{
      display : none;
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
    z-index : 1;
    width : 100%;
    height : 100%;
  }

  @media screen and (max-width : 1200px){
    img{
      width : 118%;
      margin-left : -9%;
    }
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
  margin-right : .5rem;
  }

  @media screen and (max-width:990px){
    height : 4.6875rem;
    display:flex;
    height : 100px;
    margin : 0;
  }
  @media screen and (max-width:500px){
    font-size : 1rem;
    margin-top : 0;
  }

`;
const CardAuthor = styled.div`
  width : 100%;
  height : 50px;
  display : flex;
  justify-content :space-between;
  align-items:center;
  overflow : hidden;
  padding-bottom : .625rem;

  a{
    text-decoration:none;
    color:black;
  }

  #author{
    display : flex;
    align-items : center;
    font-size : .625rem;
  }

  #profile{
    width : 1.875rem;
    height : 1.875rem;
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
    padding-bottom : 0px;
  }

  @media screen and (max-width : 500px)  {
    height : 50px;
    
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