import React from 'react'
import styled from 'styled-components'

export default function Pagination({ postsPerPage, totalPosts, paginate }) {
  const pageNumbers = [];
  for (let i = 1; i <= Math.ceil(totalPosts / postsPerPage); i++) {
    pageNumbers.push(i);
  }
  return (
    <PageNumber>
      {pageNumbers.map(number => {
        return (
          <span
            className="pageLink"
            onClick={() => {
              paginate(number)
            }}
            key={number}
          >{number}</span>
        )
      })

      }
    </PageNumber>
  )
}

const PageNumber = styled.div`
  margin : 1rem auto;
  width : 100%;
  display : flex;
  justify-content:center;

  .pageLink{
    margin : 0 0.3rem;
    cursor : pointer;
    font-size : 1.2rem;
    font-weight : 500;
  }

  .pageLink:hover{
    text-decoration : underline;
  }
`;
