import React, { useEffect, useState, useRef } from 'react'
import axios from 'axios';
import styled from 'styled-components';
import PropTypes from 'prop-types';
import Comment from '../comment/Comment';

function PostView(props) {
  const [modifyMode, setmodifyMode] = useState(false);
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

  const toggleModify = () => {
    setmodifyMode(!modifyMode);
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
      .then(comment => { setComments(comment.data) })
      .catch(error => console.log(error));

  }, [pUrl, cUrl, props])

  const reRenderComments = (newComment) => {
    setComments(comments.concat(newComment));
  }

  const [Title, setTitle] = useState(post.title);
  const [Content, setContent] = useState(post.content);

  const updatePost = () => {
    const exceptedPost = {
      title: Title,
      content: Content
    }

    return axios.put(pUrl, exceptedPost);
  }

  const handleFormSubmit = (e) => {
    e.preventDefault();
    updatePost()
      .then((response) => {
        alert('게시글 수정이 완료되었습니다.');
        window.location.href = `/api/posts/${response.data}`;
      })
      .catch(Error => console.log(Error));
  }


  const handleTitleChange = (e) => {
    setTitle(e.currentTarget.value);
  }
  const handleContentChange = (e) => {
    setContent(e.currentTarget.value);
  }

  return (
    <>
      {modifyMode ?
        <PostFormContainer>
          <form method="put"
            encType="multipart/form-data"
            onSubmit={handleFormSubmit}>
            <table>
              <tbody>
                <tr>
                  <td className="input_name">제목</td>
                  <td className="input">
                    <input
                      type="text" name="title"
                      value={Title}
                      onChange={handleTitleChange} /></td>
                </tr>
                <tr>
                  <td className="input_name content">내용</td>
                  <td className="input content">
                    <textarea
                      name="content"
                      value={Content}
                      onChange={handleContentChange}></textarea></td>
                </tr>
              </tbody>
            </table>

            <div className="btn">
              <div className="btnContainer">
                <button type="submit" className="submit_btn">수정완료</button>
                <button onClick={toggleModify} className="cancel_btn">수정취소</button>
              </div>
            </div>
          </form>
        </PostFormContainer>
        :
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
            <button onClick={toggleModify}>수정하기</button>
            <button >삭제하기</button>
          </PostContentContainer>
        </>
      }

      <Comment reRenderComments={reRenderComments} commentsList={comments} postId={props.match.params.postId} />
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

const PostFormContainer = styled.div`
  width : 60vw;
  height : 82.7vh;
  margin : 0 auto;
  margin-top : 12vh;

  table {
    margin: 0 auto;
    margin-top: 3rem;
  }
  
  .input_name {
    width: 10vw;
    text-align: center;
    background-color: black;
    color: white;
  }
  .input {
    width: 30vw;
    padding: 10px 10px;
  }
  
  .input input {
    width: 66.6%;
    height: 1.5rem;
  }
  
  .input textarea {
    resize: none;
    width: 30vw;
    height: 20rem;
  }
  
  .btn {
    margin-top: 2rem;
  }
  
  .btnContainer {
    margin: 0 auto;
    width: 30vw;
    display: flex;
    justify-content: center;
  }
  
  .btnContainer button,a {
    width: 80px;
    border: none;
    height: 30px;
    border-radius: 10px;
    font-weight: 0;
    font-size : 0.9rem;
    text-align : center;
    margin: 10px;
    display : flex;
    justify-content : center;
    align-items : center;
  }
  
  h1 {
    margin-left: 10vw;
    margin-bottom: 1rem;
  }
  
  #preview {
    padding-top: 3rem;
    margin: 0 auto;
    margin-bottom : 2rem;
    width: 40vw;
  }
  
  .preview_btn {
    background-color: #487eb0;
    color: #f5f6fa;
  }
  .submit_btn {
    background-color: #8c7ae6;
    color: #f5f6fa;
    cursor: pointer;
  }
  .cancel_btn {
    text-decoration : none;
    background-color: #c23616;
    color: #f5f6fa;
  }
  @media screen and (max-width: 1024px) {
    width: 70vw;
    
    .input_name {
      width: 18vw;
      text-align: center;
      background-color: black;
      color: white;
      font-size : 0.8rem;
    }
    .input {
      width: 56vw;
      padding: 8px 8px;
    }
    .input input {
      width: 40vw;
      height: 1.5rem;
    }
    .input textarea {
      margin-top : 10px;
      resize: none;
      width: 50vw;
      height: 20rem;
    }
  }
  
  @media screen and (max-width: 770px) {
    .input input{
      height : 1.3rem;
    }
    .input textarea{
      margin : 0;
      height : 20rem;
    }
    .content{
      height : 22rem;
    }
    .btnContainer {
      display: flex;
      flex-direction: column;
    }
    .btnContainer button {
      width: 100%;
    }
    .input_name {
      font-size: 0.7rem;
    }
  }
  
  @media scrren and (max-width : 500px){
    .input {
      width : 30vw;
    }
    .input input {
      margin : 0;
      width: 10vw;
      height: 1.3rem;
    }

    #preview {
      width : 80vw;
    }
  }
`;

export default PostView;