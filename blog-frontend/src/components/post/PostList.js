import React, { useState, useEffect } from 'react'
import styled from 'styled-components';
import axios from 'axios';
import PostCard from './PostCard';

const PostListContainer = styled.div`
  margin: 0 auto;
  margin-top : 10vh;
  width: 60vw;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;

  .post {
    display: flex;
    flex-direction: column;
    width: 18vw;
    margin-top : 1rem;
    margin-bottom : 1rem;
  }
  
  .postImg {
    height: 250px;
    background-color: turquoise;
  }

  .postImg img{
    width : 18vw;
    height : 250px;
  }
  
  .postTitle {
    display: flex;
    flex-direction: column;
    padding: 10px;
  }
  .profile {
    display: flex;
    align-items: center;
    padding: 5px 2px;
  }
  .user {
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-left: 5px;
  }
  .name {
    font-size: 0.8rem;
  }
  .date {
    font-size: 0.5rem;
  }
  .picture {
    width: 40px;
    height: 40px;
    border-radius: 40px;
    background-color: violet;
  }
  
  .dark {
    background-color: #353b48;
    color: #dedede;
  }
  
  @media screen and (max-width: 1400px) {
    .postImg {
      height: 180px;
    }
  }
  
  @media screen and (max-width: 800px) {
    .post {
      display: flex;
      flex-direction: row-reverse;
      justify-content: space-between;
      border: 1px solid #dedede;
      box-shadow: 1px 1px 2px #241566;
      width: 100%;
      height: 15vh;
      margin-top: 15px;
    }
  
    .postImg {
      width: 25%;
      background-color: tomato;
      height: 100%;
    }

    .postImg img{
      width :25%;
      height : 100%;
    }

    .postTitle {
      width: 60%;
      justify-content: space-between;
    }
    .name {
      font-size: 10px;
    }
    .date {
      font-size: 4px;
    }
    .picture {
      width: 20px;
      height: 20px;
      border-radius: 20px;
      background-color: violet;
    }
  }
  
  @media screen and (max-width: 400px) {
    .postImg {
      width: 40%;
    }

    .postImg img{
      width:40%;
    }
  }
`;




export default function PostList() {
  const url = "/api/posts";
  const [posts, setPosts] = useState([]);
  useEffect(() => {
    const getPosts = () => {
      axios.get(url)
        .then(post => {
          setPosts(post.data);
        })
        .catch(Error => {
          console.log(Error);
        });
    }

    getPosts();

  }, [posts])

  return (
    <PostListContainer>
      {posts ?
        posts.map((post, index) => {
          return (
            <PostCard {...post} key={index}></PostCard>
          )
        })
        : <div className="loading"><i className="fas fa-spinner"></i></div>
      }
    </PostListContainer >
  );
}