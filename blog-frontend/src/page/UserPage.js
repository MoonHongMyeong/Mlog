import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { BrowserRouter as Router, Route, NavLink, Switch } from 'react-router-dom';
import UserPost from './components/user/UserPost';
import UserCategory from './components/user/UserCategory';
import UserAbout from './components/user/UserAbout';
import Footer from './components/common/Footer';
import { UserPageLayout, LayoutHeight } from './components/atoms/Layouts';
import axios from 'axios';
import { Button } from './components/atoms/Buttons';

export default function UserPage(props) {
  const [OnModifyMode, setOnModifyMode] = useState(false);
  const [user, setUser] = useState({});
  const [Username, setUsername] = useState(user.name);
  const [SessionUser, setSessionUser] = useState(null);

  const getSessionUser = () => {
    axios.get('/api/v2/user')
      .then(response => {
        setSessionUser(response.data);
      }).catch(error => console.log(error))
  }


  useEffect(() => {
    if (props.match.params.userId) {
      axios.get(`/api/v2/user/${props.match.params.userId}`)
        .then(response => setUser(response.data));
    }
    getSessionUser()
  }, [props.match.params.userId])

  const handleModifyMode = () => {
    setOnModifyMode(!OnModifyMode);
  }

  const submitEditUsername = () => {
    const url = `/api/v2/user/${props.match.params.userId}`;

    axios.put(url, {
      name: Username,
    })
      .then(response => alert("수정이 완료되었습니다."))
      .catch(error => console.log(error));
  }

  const usernameChange = (e) => {
    setUsername(e.currentTarget.value);
  }

  return (
    <>
      <LayoutHeight style={{ "marginBottom": "2rem" }}>
        <UserPageLayout>
          <UserInfoLayout>
            {OnModifyMode ?
              <UserInfo>
                <img style={{
                  "width": "8rem",
                  "height": "8rem",
                  "borderRadius": "8rem",
                }} src={`${user.picture}`} alt="userimg"></img>
                <input
                  onChange={usernameChange}
                  type="text"
                  placeholder="수정할 이름을 적어주세요"
                  style={{
                    "margin": "1rem",
                    "height": "1.5rem",
                    "border": "none",
                    "borderBottom": "1px solid grey",
                  }}
                />
                <div>
                  <Button onClick={submitEditUsername}>수정완료</Button>
                  <span>  </span>
                  <Button onClick={handleModifyMode}>수정취소</Button>
                </div>
              </UserInfo>
              :
              <UserInfo>
                <img style={{
                  "width": "8rem",
                  "height": "8rem",
                  "borderRadius": "8rem",
                }} src={`${user.picture}`} alt="userimg"></img>
                <h3>{user.name}</h3>
                {SessionUser && SessionUser.id === user.id &&
                  <>
                    <div>
                      <Button onClick={handleModifyMode}>회원수정</Button>
                      <span>  </span>
                      <Button>회원탈퇴</Button>
                    </div>
                    <div>
                      <Button style={{
                        "width": "100%",
                        "marginTop": "0.3rem"
                      }}>카테고리 추가/수정/삭제</Button>
                    </div>
                  </>
                }
              </UserInfo>
            }
          </UserInfoLayout>
          <Router>
            <UserMenu>
              <NavLink to={`/api/v2/user/${props.match.params.userId}/posts`}
                className="userNav"
                activeClassName="active">포스트</NavLink>
              <NavLink to={`/api/v2/user/${props.match.params.userId}/categories`}
                className="userNav"
                activeClassName="active">시리즈</NavLink>
              <NavLink to={`/api/v2/user/${props.match.params.userId}/about`}
                className="userNav"
                activeClassName="active">소개</NavLink>
            </UserMenu>
            <Switch>
              <Route exact path={`/api/v2/user/${props.match.params.userId}/posts`} component={UserPost} />
              <Route exact path={`/api/v2/user/${props.match.params.userId}/categories`} component={UserCategory} />
              <Route exact path={`/api/v2/user/${props.match.params.userId}/about`} component={UserAbout} />
            </Switch>
          </Router>
        </UserPageLayout>
      </LayoutHeight>
      <Footer />
    </>
  )
}

const UserInfoLayout = styled.div`
  width : 100%;
  height : 25rem;
  display : flex;
  justify-content:center;
  align-items:center;
`;

const UserInfo = styled.div`
  width : 15rem;
  height : 20rem;
  display : flex;
  flex-direction : column;
  justify-content : center;
  align-items :center;
`;

const UserMenu = styled.div`
  width : 100%;
  height : 5rem;
  background-color : black;
  display : flex;
  justify-content:center;
  align-items : center;

  .userNav{
    width : 5rem;
    height : 3rem;
    text-decoration : none;
    color : white;
    font-size : 1.2rem;
    display : flex;
    justify-content : center;
    align-items:center;
  }

  .userNav:hover{
    text-decoration : underline;
  }

  .active{
    text-decoration : underline;
  }
`;