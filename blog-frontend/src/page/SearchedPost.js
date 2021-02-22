import React, { useEffect, useState } from 'react'
import PostCard from './components/posts/PostCard';
import { CardsLayout, LayoutHeight } from './components/atoms/Layouts';
import Footer from './components/common/Footer';
import axios from 'axios';
import Loading from './components/common/Loading';

export default function PostList() {
  const [isLoading, setIsLoading] = useState(false);
  const [searchPostList, setSearchPostList] = useState([]);

  useEffect(() => {
    setIsLoading(true);
    axios.get("/api/v2/searchedPost")
      .then(response => setSearchPostList(response.data))
      .catch(error => console.log(error));
    setIsLoading(false);
  }, [])
  return (
    <>
      <LayoutHeight>
        <CardsLayout>
          {isLoading && <Loading />}
          {searchPostList &&
            searchPostList.map(post => {
              return <PostCard post={post} key={post.id} />
            })}
        </CardsLayout>
      </LayoutHeight>
      <Footer />
    </>
  )
}
