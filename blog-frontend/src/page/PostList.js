import React from 'react'
import PostCard from './components/posts/PostCard';
import { CardsLayout, LayoutHeight } from './components/atoms/Layouts';
import Footer from './components/common/Footer';

export default function PostList() {

  return (
    <>
      <LayoutHeight>
        <CardsLayout>
          <PostCard />
          <PostCard />
          <PostCard />
          <PostCard />
          <PostCard />
          <PostCard />
          <PostCard />
          <PostCard />
          <PostCard />
          <PostCard />
          <PostCard />

        </CardsLayout>
      </LayoutHeight>
      <Footer />
    </>
  )
}
