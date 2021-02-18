import React from 'react';
import styled from 'styled-components';

export default function Footer() {
  return (
    <Container>
      <p>
        <span>#Contact : </span>
        <span><a href="mailto:moonhongmyeong@gmail.com">
          <i className="fas fa-envelope"></i></a></span>
        <span> , </span>
        <span><a href="https://github.com/moon4368">
          <i className="fab fa-github-square"></i></a>
        </span>
      </p>
      <p>
        <span>Design by Moon Hong Myeong ⓒ 2021</span>
      </p>
    </Container>
  )
}

const Container = styled.div`
  background-color: black;
  color: gray;
  font-family: "Frank Ruhl Libre", serif;
  display: flex;
  flex-direction: column;
  justify-items: center;
  align-items: center;
  height : 116px;

  a {
  text-decoration: none;
  color: var(--text-white-color);
  font-size: 1.2rem;
  }
`;