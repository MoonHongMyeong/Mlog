import React from 'react'
import { Link } from 'react-router-dom';
import UserPostCard from './UserPostCard';
import PlusCard from './PlusCard';
export default function UserPost() {
  return (
    <>
      <UserPostCard />
      <UserPostCard />
      <UserPostCard />
      <UserPostCard />
      <UserPostCard />
      <UserPostCard />
      <Link style={{
        "textDecoration": "none",
      }} to="/write">
        <PlusCard />
      </Link>
    </>
  )
}

