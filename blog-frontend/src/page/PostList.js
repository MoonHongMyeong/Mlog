import React, { useEffect, useState } from 'react'
import PostCard from './components/posts/PostCard';
import { CardsLayout, LayoutHeight } from './components/atoms/Layouts';
import Footer from './components/common/Footer';
import axios from 'axios';
import Loading from './components/common/Loading';


export default function PostList() {
  const [isLoading, setIsLoading] = useState(false);
  const [postList, setPostList] = useState([]);

  useEffect(() => {
    setIsLoading(true);
    axios.get("/api/v2/posts")
      .then(response => { setPostList(Array.from(response.data)); })
      .catch(error => console.log(error))
      .then(setIsLoading(false));
  }, [])


  return (
    <>
      {isLoading && <Loading />}
      <LayoutHeight>
        <CardsLayout>
          {postList &&
            postList.map(post => {
              return <PostCard post={post} key={post.id} />
            })}
          {isLoading && <Loading />}
        </CardsLayout>
      </LayoutHeight>
      <Footer />
    </>
  )
}
