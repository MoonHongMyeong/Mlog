import React from 'react';
import styled from 'styled-components';

export default function Pagination({ postsPerPage, totalPosts, paginate }) {
  const pageNumbers = [];
  for (let i = 1; i <= Math.ceil(totalPosts / postsPerPage); i++) {
    pageNumbers.push(i);
  }

  return (
    <PaginationContainer>
      <div className="pagination">
        {pageNumbers.map((number) => {
          return (<span className="pageLink" onClick={() => paginate(number)} key={number}>{number}</span>)
        })}
      </div>
    </PaginationContainer>
  )
}

const PaginationContainer = styled.div`
  margin-top : 1rem;
  margin-bottom : 1rem;

  .pagination{
    margin : 0 auto;
    width : 60vw;
    display : flex;
    justify-content : center;
  } 

  .pageLink{
    margin-left : 0.4rem;
    margin-right : 0.4rem;
    cursor: pointer;
    font-size : 1.5rem;
    font-weight : 600;
  }
  .pageLink:hover{
    text-decoration : underline;
  }
`;