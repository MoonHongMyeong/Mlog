import React, { useState } from 'react'
import styled from 'styled-components';
import { NavLink, Link } from 'react-router-dom';
import { LogoutButton } from '../atoms/Buttons';
import { HeaderLayout, MenuLayout } from '../atoms/Layouts';
import Login from '../modal/Login';

export default function Header() {
  const [onLoginModal, setOnLoginModal] = useState(false);

  return (
    <header>

      {onLoginModal && <Login />}

      <HeaderLayout>
        <Link to="/" style={{ "textDecoration": "none" }}>
          <Logo>
            <span>Mlog</span>
          </Logo>
        </Link>
        <Profile>
          <LogoutButton onClick={() => { setOnLoginModal(!onLoginModal) }}>로그인</LogoutButton>
          <Link to="/user">
            <div id="profile">
              <img src="" alt="?" />
            </div>
          </Link>
        </Profile>
      </HeaderLayout>
      <MenuLayout>
        <div>
          <NavLink to="/"><Nav><i className="fas fa-fire">인기 포스트</i></Nav></NavLink>
          <NavLink to="/form"><Nav><i className="fas fa-history">최신 포스트</i></Nav></NavLink>
        </div>
        <Search>
          <form>
            <input type="text" placeholder="검색어를 입력하세요" style={{ "borderBottom": "1px solid #acacac" }} />
            <button style={{ "borderBottom": "1px solid #acacac" }} type="submit"><i className="fas fa-search"></i></button>
          </form>
        </Search>
      </MenuLayout >
    </header>
  )
}

const Search = styled.div`
  input,button {
    border : none;
    color : #acacac;
  }
  button{
    cursor: pointer;
    background-color : white;    
  }
  input:focus,button:focus{
    outline : none;
  }

`;

const Logo = styled.div`
  width : 5rem;
  height : 3rem;
  display : flex;
  justify-content : center;
  align-items : center;
  color : #3d3d3d;
  span {
    font-family: 'Long Cang', cursive;
    font-weight : 1000;
    font-size: 2.5rem;
  }
`;

const Profile = styled.div`
width: 10rem;
  height : 3rem;
  display : flex;
  justify-content : center;
  align-items:center;
  
  #profile{
    margin-left : 1rem;
    margin-right : 0;
    border-radius : 3rem;
    width : 2.5rem;
    height : 2.5rem;
    background-color:black;
    &:hover{
      opacity : 0.7;
    }
  }
  #profile img{
    width : 100%;
    height :100%;
  }
`;

const Nav = styled.span`
  color : #4b4b4b;
  margin : .5rem;
  opacity : 0.5;
`;

