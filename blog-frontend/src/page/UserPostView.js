import React, { useState, useEffect } from 'react'
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { PostViewLayout, SearchLayoutHeight, PostLayout } from './components/atoms/Layouts';
import { TitleInput, FormTextarea } from './components/atoms/Inputs';
import { Button, FormButton } from './components/atoms/Buttons';
import Comment from './components/comments/Comments';
import axios from 'axios';
import Loading from './components/common/Loading';
import PostTitle from './components/posts/PostTitle';

export default function UserPostView(props) {
  console.log(props);
  const [isLoading, setIsLoading] = useState(true);
  const [modifyMode, setModifyMode] = useState(false);

  const [posts, setPosts] = useState({});
  const [comments, setComments] = useState([]);

  const windowHeight = (window.innerHeight - 300) + 'px';

  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [exceptedContent, setExceptedContent] = useState("");
  const [userId, setUserId] = useState(0);
  const [name, setName] = useState("");
  const [picture, setPicture] = useState("")
  const [about, setAbout] = useState("소개가 없습니다.");
  const [SessionUser, setSessionUser] = useState(null);
  const [LikeVal, setLikeVal] = useState(false);
  const [LikeCount, setLikeCount] = useState(0);
  const pUrl = `${props.match.params.postId}`;
  const cUrl = `${props.match.params.postId}/comments`;

  const handleModifyMode = () => {
    setModifyMode(!modifyMode);
  }

  useEffect(() => {
    axios.get('/api/v2/user')
      .then(response => {
        setSessionUser(response.data);
      }).catch(error => console.log(error));

    axios.get(pUrl)
      .then(response => {
        setPosts(response.data);
        setTitle(response.data.title);
        setContent(response.data.content);
        setUserId(response.data.user.id);
        setName(response.data.user.name);
        setPicture(response.data.user.picture)
        setAbout(response.data.user.about);
        setLikeCount(response.data.likeCount);
        setLikeVal(response.data.like_val);
      })
      .catch(error => console.log(error));

    axios.get(cUrl)
      .then(response => setComments(response.data))
      .catch(error => console.log(error));


    setIsLoading(false);
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
    if (window.confirm("포스트를 삭제하시겠습니까?")) {
      axios.delete(pUrl).then(alert("포스트가 삭제 되었습니다."));
      window.location.href = "/";
    }
  }

  const handleTitleChange = (e) => {
    setTitle(e.target.value);
  }
  const handleContentChange = (e) => {
    setContent(e.target.value);
  }


  const submitEditPost = (e) => {
    setExceptedContent(content);
    if (content !== exceptedContent) {
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
    } else {
      alert("변경사항이 없습니다.")
    }
  }

  const submitLike = () => {
    if (SessionUser === null) {
      alert("로그인이 필요합니다.")
    } else {
      axios.get(`${props.match.params.postId}/like`)
        .then(response => {
          setLikeVal(true);
          setLikeCount(LikeCount + 1);
        })
        .catch(error => console.log(error));
    }
  }
  const submitDislike = () => {
    axios.get(`${props.match.params.postId}/disLike`)
      .then(response => {
        setLikeVal(false);
        setLikeCount(LikeCount - 1);
      })
      .catch(error => console.log(error));
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
                  <FormButton onClick={submitEditPost}>수정완료</FormButton>
                </div>
              </FormTools>
            </>
            :
            <>
              <SearchLayoutHeight>
                <PostViewLayout>
                  <PostTitle posts={posts} />
                  <Link to={`/api/v2/user/${userId}`} style={{ "textDecoration": "none" }}>
                    <div style={{
                      "display": "flex",
                      "marginTop": "3rem",
                      "marginBottom": "3rem"
                    }}>
                      <UserImg>
                        <img src={picture} alt="userProfile"></img>
                      </UserImg>
                      <UserProfile>
                        <span style={{
                          "fontSize": "1.2rem",
                          "fontWeight": "800",
                          "wordBreak": "break-all",
                          "marginLeft": "0.3rem",
                          "color": "black"
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
                  </Link>
                  <div className="content" style={{
                    "wordBreak": "break-all",
                    "whiteSpace": "pre-line"
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
                    {
                      LikeVal ?
                        <Button
                          color="black"
                          onClick={submitDislike}
                        >
                          <i className="far fa-thumbs-up"></i> {LikeCount}
                        </Button>
                        :
                        <Button
                          color="grey"
                          onClick={submitLike}>
                          <i className="far fa-thumbs-up"></i> {LikeCount}
                        </Button>
                    }


                  </div>
                  {SessionUser && SessionUser?.id === posts.user?.id &&
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
                    SessionUser={SessionUser}
                  />
                </PostViewLayout>
              </SearchLayoutHeight>
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