import React, { useEffect, useState } from 'react'
import PostCard from './components/posts/PostCard';
import { CardsLayout, LayoutHeight } from './components/atoms/Layouts';
import Footer from './components/common/Footer';
import axios from 'axios';
import Loading from './components/common/Loading';
import Pagination from './components/posts/Pagination';

export default function PostList() {
  const [isLoading, setIsLoading] = useState(true);
  const [postList, setPostList] = useState([]);

  const [currentPage, setCurrentPage] = useState(1);
  const postsPerPage = 12;
  const indexOfLast = currentPage * postsPerPage;
  const indexOfFirst = indexOfLast - postsPerPage;

  const currentPosts = (temp) => {
    let currentPosts = 0;
    currentPosts = temp.slice(indexOfFirst, indexOfLast);
    return currentPosts;
  }

  useEffect(() => {
    setIsLoading(true);
    axios.get("/api/v2/posts")
      .then(response => { setPostList(Array.from(response.data)); })
      .catch(error => console.log(error))
      .then(setIsLoading(false));
  }, [])


  return (
    <>
      {isLoading ? <Loading />
        :
        <>
          <LayoutHeight>
            <CardsLayout>
              {postList &&
                <PostCard post={currentPosts(postList)} />
              }
              {isLoading && <Loading />}
            </CardsLayout>

          </LayoutHeight>
          <Pagination
            postsPerPage={postsPerPage}
            totalPosts={postList.length}
            paginate={setCurrentPage} />
          <Footer />
        </>
      }</>
  )
}
