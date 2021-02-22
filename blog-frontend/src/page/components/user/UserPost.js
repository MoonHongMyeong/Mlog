import React from 'react'
import { Link } from 'react-router-dom';
import UserPostCard from './UserPostCard';

export default function UserPost() {
  return (
    <>
      <UserPostCard />
      <Link style={{
        "textDecoration": "none",
      }} to="/api/v2/write">
      </Link>
    </>
  )
}

