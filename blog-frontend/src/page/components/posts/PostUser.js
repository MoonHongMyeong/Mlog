import React from 'react'
import styled from 'styled-components';
export default function PostUser() {
  return (
    <div style={{
      "display": "flex",
      "marginTop": "3rem",
      "marginBottom": "3rem"
    }}>
      <UserImg>
        <img src="./images/default.png" alt="userProfile"></img>
      </UserImg>
      <UserProfile>
        <span style={{
          "fontSize": "1.2rem",
          "fontWeight": "800",
          "wordBreak": "break-all",
          "marginLeft": "0.3rem"
        }}>User.name</span>
        <p style={{
          "fontSize": "0.8rem",
          "color": "grey",
          "wordBreak": "break-all"
        }}>user.descriptionasdfdasfdasfasdfasdfasdfasdfasdfasdfasadasdasdasdassdfasdfasdfsdafsdfsadfsadfasdf</p>
      </UserProfile>

    </div>
  )
}

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