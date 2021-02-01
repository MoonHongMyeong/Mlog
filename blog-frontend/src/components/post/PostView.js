import React, { useEffect, useState, useRef } from 'react'
import axios from 'axios';
import styled from 'styled-components';
import PropTypes from 'prop-types';
import Comment from '../comment/Comment';

function PostView(props) {

  const [post, setPost] = useState({});
  const [comments, setComments] = useState([]);
  const backgroundRef = useRef();
  const titleRef = useRef();
  const dateRef = useRef();

  post.propTypes = {
    id: PropTypes.number.isRequired,
    title: PropTypes.string.isRequired,
    content: PropTypes.string.isRequired,
    author: PropTypes.string.isRequired,
    modifiedDate: PropTypes.string.isRequired
  }

  comments.propTypes = {
    id: PropTypes.number.isRequired,
    author: PropTypes.string.isRequired,
    body: PropTypes.string.isRequired,
    parents: PropTypes.number,
    modifiedDate: PropTypes.string.isRequired
  }

  const pUrl = `${props.match.params.postId}`;
  const cUrl = `${props.match.params.postId}/comments`;

  useEffect(() => {
    axios.get(pUrl)
      .then(post => setPost(post.data))
      .then(backgroundRef.current.style.opacity = "0.7", backgroundRef.current.style.transition = "opacity 1s ease-in-out")
      .then(titleRef.current.style.opacity = "1",
        titleRef.current.style.transition = "opacity 3s ease-in-out",
        dateRef.current.style.opacity = "1",
        dateRef.current.style.transition = "opacity 3s ease-in-out")
      .catch(error => console.log(error));

    axios.get(cUrl)
      .then(comment => {
        setComments(comment.data);
      })
      .catch(error => console.log(error));

  }, [pUrl, cUrl, props])

  const reRenderComments = (newComment) => {
    setComments(comments.concat(newComment));
  }

  return (
    <>
      <PostTitleContainer>
        <div className="titleImg">
          <img src={post.imageUrl} alt={post.title}></img>
          <div className="title_background" ref={backgroundRef}>
            <h1 className="post_title" ref={titleRef}>{post.title}</h1>
            <p className="post_time" ref={dateRef}>{post.modifiedDate}</p>
          </div>
        </div>
      </PostTitleContainer>
      <PostContentContainer>
        <div className="author" >
          <div className="picture"></div>
          <div className="user">
            <h2>{post.author}</h2>
            <p>{post.author}</p>
          </div>
        </div >
        <div id="markdown_content">
          {post.content}
        </div>
        <button >수정하기</button>
        <button >삭제하기</button>
      </PostContentContainer>
      <Comment reRenderComments={reRenderComments} commentsList={comments} post={props.match.params.postId} />
    </>
  )
}

const PostTitleContainer = styled.div`
  padding: 0;
  margin: 0;

  .titleImg {
    background-color: #00a8ff;
    width: 100%;
    height: 100vh;
  }
  .titleImg img{
    width : 100%;
    height :100%;
  }
  .title_background {
    background-color: black;
    position: absolute;
    opacity: 0.5;
    top : 0;
    left : 0;
    width: 100%;
    height: 100vh;
  }
  .post_title {
    height : 85%;
    color: white;
    display: flex;
    justify-content: center;
    align-items: center;
    opacity: 0;
  }
  .post_time {
    color: white;
    text-align: center;
    opacity : 0;
  }
`;

const PostContentContainer = styled.div`
  width: 60vw;
  margin: 0 auto;
  display: flex-column;
  justify-content: center;
  flex-wrap: wrap;

  .author {
    display: flex;
    align-items : center;
    width : 900px;
    height : 200px;
    padding: 10px 10px;
  }
  .picture {
    width: 130px;
    height: 130px;
    border-radius: 130px;
    background-color: yellowgreen;
  }
  .user {
    margin-left: 1rem;
    height : 200px;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  .user span {
    color: #cbcbcb;
  }
  #markdown_content {
    padding-top: 2rem;
    border-top: 1px solid #000;
    font-family: "Noto Sans KR", sans-serif;
  }
  #markdown_content textarea{
    width : 47.5vw;
    height : 500px;
    resize:none;
  }

  @media screen and (max-width : 800px){
    .author{
      width : 650px;
    }
  }

  @media screen and (max-width : 500px){
    .author {
      width : 280px;
      height : 150px;
    }
    .picture{
      width : 80px;
      height : 80px;
    }
  }
`;


export default PostView;