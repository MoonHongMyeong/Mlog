import React from 'react';
import styled from 'styled-components';
import { BrowserRouter as Router, Route, NavLink, Switch } from 'react-router-dom';
import UserPost from './components/user/UserPost';
import UserCategory from './components/user/UserCategory';
import UserAbout from './components/user/UserAbout';
import Footer from './components/common/Footer';
import { UserPageLayout, LayoutHeight } from './components/atoms/Layouts';
export default function UserPage() {
  return (
    <>
      <LayoutHeight>
        <UserPageLayout>
          <UserInfoLayout>
            <UserInfo>
              <img style={{
                "width": "8rem",
                "height": "8rem",
                "borderRadius": "8rem",
              }} src="" alt="userimg"></img>
              <h3>user.name</h3>
              <span style={{
                "color": "grey",
                "wordBreak": "break-all",
              }}>descriptionsssssssssssssssssssssssssssssssssssssssssssssssssss</span>
            </UserInfo>
          </UserInfoLayout>
          <Router>
            <UserMenu>
              <NavLink to="/user/posts" className="userNav" activeClassName="active">포스트</NavLink>
              <NavLink to="/user/categories" className="userNav" activeClassName="active">시리즈</NavLink>
              <NavLink to="/user/about" className="userNav" activeClassName="active">소개</NavLink>
            </UserMenu>
            <Switch>
              <Route exact path="/user/posts" component={UserPost} />
              <Route exact path="/user/categories" component={UserCategory} />
              <Route exact path="/user/about" component={UserAbout} />
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