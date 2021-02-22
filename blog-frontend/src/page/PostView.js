import React, { useState, useEffect } from 'react'
import styled from 'styled-components';
import { PostViewLayout, LayoutHeight, PostLayout } from './components/atoms/Layouts';
import PostTitle from './components/posts/PostTitle';
import { TitleInput, FormTextarea } from './components/atoms/Inputs';
import { Button, FormButton } from './components/atoms/Buttons';
import Footer from './components/common/Footer';
import Comment from './components/comments/Comments';
import axios from 'axios';
import Loading from './components/common/Loading';

export default function PostView(props) {
  const [isLoading, setIsLoading] = useState(true);
  const [modifyMode, setModifyMode] = useState(false);

  const [posts, setPosts] = useState({});
  const [comments, setComments] = useState([]);

  const windowHeight = (window.innerHeight - 300) + 'px';

  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [name, setName] = useState("");
  const [about, setAbout] = useState("소개가 없습니다.");
  const [valTitle, setvalTitle] = useState(false);
  const [valContent, setvalContent] = useState(false);

  const [SessionUser, setSessionUser] = useState(null);

  //이거는 리덕스 안쓰면 미친놈이겠다
  const getSessionUser = () => {
    axios.get('/api/v2/user')
      .then(response => {
        setSessionUser(response.data);
      }).catch(error => console.log(error));
  }

  const pUrl = `${props.match.params.postId}`;
  const cUrl = `${props.match.params.postId}/comments`;

  const handleModifyMode = () => {
    setModifyMode(!modifyMode);
  }

  useEffect(() => {
    axios.get(pUrl)
      .then(response => {
        setPosts(response.data);
        setTitle(response.data.title);
        setContent(response.data.content);
        setName(response.data.user.name);
        setAbout(response.data.user.about);
      })
      .catch(error => console.log(error))
    axios.get(cUrl)
      .then(response => setComments(response.data))
      .catch(error => console.log(error))

    getSessionUser();
    posts && comments && setIsLoading(false);
    //해당 포스트에 접속한 유저의 좋아요 유무
  }, [pUrl, cUrl])

  const reRenderCommentsAdd = (newComment) => {
    setComments(comments.concat(newComment));
  }

  const reRenderCommentsUpdate = () => {
    axios.get(cUrl)
      .then(response => { setComments(response.data) })
      .catch(error => console.log(error));
  }


  const handleDeletePost = () => {
    window.confirm("포스트를 삭제하시겠습니까?");
    axios.delete(pUrl).then(alert("포스트가 삭제 되었습니다."));
    window.location.href = "/";
  }

  const handleTitleChange = (e) => {
    setTitle(e.target.value);
    setvalTitle(true);
  }
  const handleContentChange = (e) => {
    setContent(e.target.value);
    setvalContent(true);
  }


  const handleEditPost = () => {
    if (valTitle && valContent) {
      const exceptedPost = {
        title: title,
        content: content
      }
      axios.put(pUrl, exceptedPost)
        .then(response => {
          alert("수정이 완료되었습니다.");
          axios.get(pUrl).then(response => {
            setPosts(response.data);
            setTitle(response.data.title);
            setContent(response.data.content);
          })
          props.history.push(props.location.pathname);
          setModifyMode(false);
        }).catch(error => console.log(error));
    }
  }

  return (
    <>
      {isLoading ? <Loading /> : <>
        {
          modifyMode ?
            <>
              <PostLayout>
                <TitleInput name="title" onChange={handleTitleChange} value={title} placeholder="제목을 입력하세요." />
                <FormTextarea name="content" onChange={handleContentChange} value={content} placeholder="내용을 입력하세요." height={windowHeight}></FormTextarea>
              </PostLayout>
              <FormTools>
                <div>
                  <FormButton onClick={handleModifyMode}><i className="fas fa-arrow-left"></i> 나가기</FormButton>
                </div>
                <div>
                  <FormButton>미리보기</FormButton>
                  <FormButton onClick={handleEditPost}>수정완료</FormButton>
                </div>
              </FormTools>
            </>
            :
            <>
              <LayoutHeight>
                <PostViewLayout>
                  <PostTitle posts={posts} />
                  <div style={{
                    "display": "flex",
                    "marginTop": "3rem",
                    "marginBottom": "3rem"
                  }}>
                    <UserImg>
                      <img src="/images/default.png" alt="userProfile"></img>
                    </UserImg>
                    <UserProfile>
                      <span style={{
                        "fontSize": "1.2rem",
                        "fontWeight": "800",
                        "wordBreak": "break-all",
                        "marginLeft": "0.3rem"
                      }}>
                        {name}
                      </span>
                      <p style={{
                        "fontSize": "0.8rem",
                        "color": "grey",
                        "wordBreak": "break-all"
                      }}>
                        {about === null ? "소개가 없습니다." : about}
                      </p>
                    </UserProfile>
                  </div>
                  <div className="content" style={{
                    "wordBreak": "break-all",
                    "whiteSpace": "pre-line"
                    //이 부분은 텍스트에어리어 안에서 작성한 글 가져와서 봐야할듯?
                  }}>
                    {posts.content}
                  </div>
                  <div className="like" style={{
                    "display": "flex",
                    "justifyContent": "center",
                    "alignItems": "center",
                    "marginTop": "2rem",
                    "marginBottom": "1rem"
                  }}>
                    <Button color="grey"><i className="far fa-thumbs-up"></i>   {posts.likeCount}  </Button>
                  </div>
                  {SessionUser && SessionUser.id === posts.user.id &&
                    <AuthorTool>
                      <Button style={{ "marginRight": "1rem" }}
                        onClick={handleModifyMode}
                      >수정하기</Button>
                      <Button onClick={handleDeletePost}>삭제하기</Button>
                    </AuthorTool>
                  }
                  <Comment
                    reRenderCommentsAdd={reRenderCommentsAdd}
                    reRenderCommentsUpdate={reRenderCommentsUpdate}
                    comments={comments}
                    postId={props.match.params.postId}
                  />
                </PostViewLayout>
              </LayoutHeight>
              <Footer />
            </>
        }
      </>}
    </>
  )
}

const AuthorTool = styled.div`
  display : flex;
  justify-content : center;
  align-items:center;
  margin : 1rem 0rem;
  padding : 2rem 0rem;
  border-top : 1px solid skyblue;
  border-bottom : 1px solid skyblue;
`;

const UserProfile = styled.div`
  width : 50%;
  height : 8rem;
  overflow: hidden;
  @media screen and (max-width:990px){
    height : 5rem;
    p{
    margin : 0.5rem;
    }
  }
`;

const UserImg = styled.div`
margin-right : 2rem;
  width : 16%;
  height : 8rem;
  border-radius : 8rem;
  overflow : hidden;
  img {
    width : 100%;
    height : 100%;
  }
  @media screen and (max-width:990px){
    width : 5rem;
    height : 5rem;
    img{
      width : 200%;
      height : 200%;
      margin : -50%;
    }
  }
`;

const FormTools = styled.div`
width: 100%;
position: absolute;
height: 4rem;
bottom: 0;
display:flex;
justify-content: space-evenly;
align-items : center;

button {
  margin : 0.5rem;
}
`;