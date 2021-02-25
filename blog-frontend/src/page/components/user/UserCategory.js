import React, { useState, useEffect } from 'react'
import CategoryCard from './UserCategoryCard';
import axios from 'axios';
export default function UserCategory(props) {
  const [Categories, setCategories] = useState([]);

  useEffect(() => {
    axios.get(props.location.pathname)
      .then(response => {
        setCategories(Array.from(response.data));
      })
  }, [props])

  return (
    <>
      {Categories.length === 0 ?
        <div style={{
          "textAlign": "center",
          "marginTop": "2rem"
        }}>시리즈가 존재하지 않습니다</div> :
        <>
          {
            Categories.map(category => {
              return <CategoryCard category={category} key={category.id} />
            })
          }
        </>
      }
    </>
  )
}
