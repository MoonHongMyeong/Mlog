import React, { useState, useEffect } from 'react'
import styled from 'styled-components';
import { NavLink, Link } from 'react-router-dom';
import { LogoutButton, LoginButton, NewPostButton } from '../atoms/Buttons';
import { HeaderLayout, MenuLayout } from '../atoms/Layouts';
import Login from '../modal/Login';
import axios from 'axios';

function Header(props) {
  const [onLoginModal, setOnLoginModal] = useState(false);
  const [isLogined, setIsLogined] = useState(false);
  const [LoginUser, setLoginUser] = useState("");
  const [hide, setHide] = useState(false);
  const [currentHeight, setCurrentHeight] = useState(0);


  useEffect(() => {
    axios.get('/api/v2/user')
      .then(response => {
        if (response.data === "") {
          setIsLogined(false);
        } else {
          setLoginUser(response.data);
          setIsLogined(true);
        }
      });

    document.addEventListener("scroll", handleScroll);

    return () => {
      document.removeEventListener("scroll", handleScroll);
    }
  }, [currentHeight])

  const handleScroll = () => {
    const { pageYOffset } = window;
    const deltaY = pageYOffset - currentHeight;
    const hide = pageYOffset !== 0 && deltaY >= 0;
    setHide(hide);
    setCurrentHeight(pageYOffset);
  }

  const handleLoginModal = () => {
    setOnLoginModal(!onLoginModal);
  }

  const handleLogout = () => {
    axios.get('/logout').then(response => {
      window.location.href = "/";
      alert("로그아웃 했습니다.")
    });
  }

  return (
    <header>
      {onLoginModal && <Login handleLoginModal={handleLoginModal} />}
      <HeaderWrap className={hide && 'hide'}>
        <HeaderLayout>
          <a href="/" style={{ "textDecoration": "none" }}>
            <Logo>
              <span>Mlog</span>
            </Logo>
          </a>

          <Profile>
            {isLogined ?
              <>
                <Link to="/api/v2/write"><NewPostButton>새글쓰기</NewPostButton></Link>
                <LogoutButton onClick={handleLogout}>로그아웃</LogoutButton>
                <Link to={`/api/v2/user/${LoginUser.id}`}>
                  <div id="profile">
                    <img src={`${LoginUser.picture}`} alt="?" />
                  </div>
                </Link>
              </>
              :
              <LoginButton onClick={handleLoginModal}>로그인</LoginButton>}



          </Profile>
        </HeaderLayout>
        <MenuLayout>
          <div>
            <NavLink style={{
              "color": "#4b4b4b",
              "textDecoration": "none",
              "opacity": "0.5",
              "margin": "0.5rem"
            }} to="/api/v2/popPosts"><i className="fas fa-fire">인기 포스트</i></NavLink>
            <NavLink to="/" style={{
              "color": "#4b4b4b",
              "textDecoration": "none",
              "opacity": "0.5",
              "margin": "0.5rem"
            }}><i className="fas fa-history">최신 포스트</i></NavLink>
          </div>
          <Search>

            <Link to="/api/v2/searchedPosts">
              <button><i className="fas fa-search"></i></button>
            </Link>

          </Search>
        </MenuLayout >
      </HeaderWrap>
    </header>
  )
}



export default Header

const HeaderWrap = styled.div`
    position: fixed;
    top: 0;
    left: 0;
    z-index: 1;
    width: 100%;
    height: 7rem;
    transition: .4s ease;
    background-color : white;
    border-bottom : 1px solid black;
    &.hide {
        transform: translateY(-7rem);
    }
`;

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
width: 15rem;
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
    overflow : hidden;
    &:hover{
      opacity : 0.7;
    }
  }
  #profile img{
    width : 100%;
    height :100%;
  }
`;
