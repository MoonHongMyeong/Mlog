import React, { useEffect, useState } from 'react'
import axios from 'axios';
import styled from 'styled-components';
import PropTypes from 'prop-types';
import Comment from '../comment/Comment';

function PostView(props) {
  const [modifyMode, setmodifyMode] = useState(false);
  const [post, setPost] = useState({});
  const [comments, setComments] = useState([]);

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
      .then(post => {
        setPost(post.data);
        setTitle(post.data.title);
        setContent(post.data.content);
      })

      .catch(error => console.log(error));

    axios.get(cUrl)
      .then(comment => { setComments(comment.data) })
      .catch(error => console.log(error));

  }, [pUrl, cUrl])

  const reRenderCommentsAdd = (newComment) => {
    setComments(comments.concat(newComment));
  }

  const reRenderCommentUpdate = () => {
    //1-1 수정된 댓글의 위치 찾기
    //comments state에서 현재 자신의 위치를 찾고 수정된 데이터를 반환받아서
    //1-2 splice(현재 자신의 위치, 1, 반환받은 데이터) 하면 될 것 같은데

    //2-1 axios로 새로 통신해서 가져온 데이터 setComment
    axios.get(cUrl)
      .then(comment => { setComments(comment.data) })
      .catch(error => console.log(error));

  }

  // const reRenderCommentsDelete = () => {
  //1-1수정된 댓글의 위치 찾기
  //1-2 splice(현재 자신의 위치, 1)이용

  //2-1 axios로 새로 통신해서 가져온 데이터 setComment
  //이방법은 데이터가 많아지면 불러와야하는 데이터들이 많아져서 문제 생기긴할건데
  //이렇게 하면 업데이트랑 같은 로직이라서 한개만 써도 된다
  // }

  const [Title, setTitle] = useState(post.title);
  const [Content, setContent] = useState(post.content);
  const [validateTitle, setvalidateTitle] = useState(true);
  const [validateContent, setvalidateContent] = useState(true);

  const updatePost = () => {
    const exceptedPost = {
      title: Title,
      content: Content
    }

    return axios.put(pUrl, exceptedPost);
  }

  const validation = () => {
    if (Title === '' || Title === ' ') {
      setvalidateTitle(false);
    } else {
      setvalidateTitle(true);
    } if (Content === '' || Content === ' ') {
      setvalidateContent(false);
    } else {
      setvalidateContent(true);
    }
  }

  const handleFormSubmit = (e) => {
    e.preventDefault();
    validation();
    if (validateTitle && validateContent) {
      updatePost()
        .then((response) => {
          alert('게시글 수정이 완료되었습니다.');
          window.location.href = `/api/posts/${response.data}`;
        })
        .catch(Error => console.log(Error))
    };
  }


  const handleTitleChange = (e) => {
    setTitle(e.currentTarget.value);
  }
  const handleContentChange = (e) => {
    setContent(e.currentTarget.value);
  }

  const deletePost = (e) => {
    e.preventDefault();
    if (window.confirm("정말로 삭제하시겠습니까?")) {
      axios.delete(pUrl).then(response => {
        alert("삭제가 완료되었습니다.");
        window.location.href = `/`
      })
        .catch(error => console.log(error))
    }

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
                      onChange={handleTitleChange} />{!validateTitle && <span style={{ "color": "red" }}>제목을 입력해주세요</span>}</td>
                </tr>
                <tr>
                  <td className="input_name content">내용</td>
                  <td className="input content">
                    <textarea
                      name="content"
                      spellCheck="false"
                      value={Content}
                      onChange={handleContentChange}></textarea>{!validateContent && <span style={{ "color": "red" }}>내용을 입력해주세요</span>}</td>
                </tr>
              </tbody>
            </table>

            <div className="btn">
              <div className="btnContainer">
                <button type="submit" className="submit_btn">수정완료</button>
                <button onClick={() => { window.location.reload() }} className="modify_cancel_btn">수정취소</button>
              </div>
            </div>
          </form>
        </PostFormContainer>
        :
        <>
          <PostTitleContainer>
            <div className="titleImg">
              <img src={post.imageUrl} alt={post.title}></img>
              <div className="title_background">
                <h1 className="post_title">{post.title}</h1>
                <p className="post_time">{post.modifiedDate}</p>
              </div>
            </div>
          </PostTitleContainer>
          <PostContentContainer>
            <div className="author" >
              <div className="picture"></div>
              <div className="user">
                <h2>{post.author}</h2>
              </div>
            </div >
            <div id="markdown_content">
              {post.content}
            </div>
            <div className="btnContainer">
              <button onClick={toggleModify} className="submit_btn">수정하기</button>
              <button onClick={deletePost} className="cancel_btn">삭제하기</button>
            </div>
          </PostContentContainer>
        </>
      }

      <Comment
        reRenderCommentsAdd={reRenderCommentsAdd}
        reRenderCommentUpdate={reRenderCommentUpdate}
        commentsList={comments}
        postId={props.match.params.postId} />
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
    top : 0;
    left : 0;
    width: 100%;
    height: 100vh;
    opacity : 0.7;
    -webkit-animation-name : fadeOut;
    -webkit-animation-duration : 1s;
    -webkit-animation-timing-function : ease-in-out;
    -moz-animation-name : fadeOut;
    -moz-animation-duration : 1s;
    -moz-animation-timing-function : ease-in-out;
    -ms-animation-name : fadeOut;
    -ms-animation-duration : 1s;
    -ms-animation-timing-function : ease-in-out;
    -o-animation-name : fadeOut;
    -o-animation-duration : 1s;
    -o-animation-timing-function : ease-in-out;
    animation-name : fadeOut;
    animation-duration : 1s;
    animation-timing-function : ease-in-out;
  }
  .post_title {
    height : 85%;
    color: white;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .post_time {
    color: white;
    text-align: center;
  }
  @-webkit-keyframes fadeOut{
    from{
      opacity : 0.2;
    }to{
      opacity : 0.7;
    }
  }
  @-moz-keyframes fadeOut{
    from{
      opacity : 0.2;
    }to{
      opacity : 0.7;
    }
  }
  @-ms-keyframes fadeOut{
    from{
      opacity : 0.2;
    }to{
      opacity : 0.7;
    }
  }
  @-o-keyframes fadeOut{
    from{
      opacity : 0.2;
    }to{
      opacity : 0.7;
    }
  }
  @keyframes fadeOut{
    from{
      opacity : 0.2;
    }to{
      opacity : 0.7;
    }
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
    .user{
      height : 150px;
    }
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
  height : 600px;
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
  .modify_cancel_btn {
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