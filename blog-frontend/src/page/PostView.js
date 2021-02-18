import React, { useState } from 'react'
import styled from 'styled-components';
import { PostViewLayout, LayoutHeight } from './components/atoms/Layouts';
import PostTitle from './components/posts/PostTitle';
import PostUser from './components/posts/PostUser';
import { Button } from './components/atoms/Buttons';
import Footer from './components/common/Footer';
import Comment from './components/comments/Comments';
import PostForm from './PostForm';
export default function PostView() {
  const [modifyMode, setModifyMode] = useState(false);

  const handleModifyMode = () => {
    setModifyMode(!modifyMode);
  }
  return (
    <>
      {modifyMode ?
        <>
          <PostForm />
        </>
        :
        <>
          <LayoutHeight>
            <PostViewLayout>
              <PostTitle />
              <PostUser />
              <div className="content" style={{
                "wordBreak": "break-all",
                "whiteSpace": "pre-line"
                //이 부분은 텍스트에어리어 안에서 작성한 글 가져와서 봐야할듯?
              }}>
                테스트 포스트 글 ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
              </div>
              <div className="like" style={{
                "display": "flex",
                "justifyContent": "center",
                "alignItems": "center",
                "marginTop": "2rem",
                "marginBottom": "1rem"
              }}>
                <Button color="grey"><i className="far fa-thumbs-up"></i>   12  </Button>
              </div>
              <AuthorTool>
                <Button style={{ "marginRight": "1rem" }}
                  onClick={handleModifyMode}
                >수정하기</Button>
                <Button>삭제하기</Button>
              </AuthorTool>
              <Comment />
            </ PostViewLayout>
          </LayoutHeight>
          <Footer />
        </>
      }
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
