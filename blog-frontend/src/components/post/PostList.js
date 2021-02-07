import React, { useState, useEffect } from 'react'
import styled from 'styled-components';
import axios from 'axios';
import PostCard from './PostCard';
import Pagination from './Pagination'

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
    border-left : 1px solid #353b48;
    border-bottom : 1px solid #353b48;
    box-shadow : 5px 5px 3px #353b48;
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

  .postTitle a {
    text-decoration : none;
    color : black;
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
  const [Loading, setLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const postsPerPage = 12;

  useEffect(() => {
    setLoading(true);
    axios.get(url)
      .then(post => {
        setPosts(post.data);
        setLoading(false);
      })
      .catch(Error => {
        console.log(Error);
      });
  }, [])

  //pagination
  const indexOfLast = currentPage * postsPerPage;
  const indexOfFirst = indexOfLast - postsPerPage;

  const currentPosts = (tmp) => {
    let currentPosts = 0;
    currentPosts = tmp.slice(indexOfFirst, indexOfLast);
    return currentPosts;
  }

  return (
    <>
      {Loading ?
        <div className="loading"><i className="fas fa-spinner"></i></div>
        :
        <>
          <PostListContainer>
            <PostCard posts={currentPosts(posts)}></PostCard>
          </PostListContainer >
          <Pagination postsPerPage={postsPerPage} totalPosts={posts.length} paginate={setCurrentPage} />
        </>
      }
    </>
  );
}