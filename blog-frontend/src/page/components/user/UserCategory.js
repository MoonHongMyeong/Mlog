import React from 'react'
import { Link } from 'react-router-dom';
import CategoryCard from './UserCategoryCard';
import PlusCard from './PlusCard';
export default function UserCategory() {
  return (
    <>
      <CategoryCard />
      <Link style={{
        "textDecoration": "none",
      }} to="/user">
        <PlusCard />
      </Link>
    </>
  )
}
