import React, { useEffect, useState } from 'react'
import PostCard from './components/posts/PostCard';
import { CardsLayout, LayoutHeight } from './components/atoms/Layouts';
import Footer from './components/common/Footer';
import axios from 'axios';
import Loading from './components/common/Loading';
import Pagination from './components/posts/Pagination';

export default function PopPosts() {
  const [isLoading, setIsLoading] = useState(true);
  const [popPostList, setPopPostList] = useState([]);

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
    axios.get("/api/v2/popPosts")
      .then(response => setPopPostList(response.data))
      .catch(error => console.log(error));
    setIsLoading(false);
  }, [])

  return (
    <>
      {isLoading ? <Loading />
        :
        <>
          <LayoutHeight>
            <CardsLayout>
              {popPostList &&
                <PostCard post={currentPosts(popPostList)} />
              }
              {isLoading && <Loading />}
            </CardsLayout>

          </LayoutHeight>
          <Pagination
            postsPerPage={postsPerPage}
            totalPosts={popPostList.length}
            paginate={setCurrentPage} />
          <Footer />
        </>
      }</>
  )
}