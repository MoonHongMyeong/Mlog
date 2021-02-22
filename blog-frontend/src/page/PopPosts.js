import React, { useEffect, useState } from 'react'
import PostCard from './components/posts/PostCard';
import { CardsLayout, LayoutHeight } from './components/atoms/Layouts';
import Footer from './components/common/Footer';
import axios from 'axios';
import Loading from './components/common/Loading';

export default function PopPosts() {
  const [isLoading, setIsLoading] = useState(false);
  const [popPostList, setPopPostList] = useState([]);

  useEffect(() => {
    setIsLoading(true);
    axios.get("/api/v2/popPosts")
      .then(response => setPopPostList(response.data))
      .catch(error => console.log(error));
    setIsLoading(false);
  }, [])

  return (
    <>
      {isLoading && <Loading />}
      <LayoutHeight>
        <CardsLayout>
          {popPostList &&
            popPostList.map(post => {
              return <PostCard post={post} key={post.id} />
            })}
        </CardsLayout>
      </LayoutHeight>
      <Footer />
    </>
  )
}