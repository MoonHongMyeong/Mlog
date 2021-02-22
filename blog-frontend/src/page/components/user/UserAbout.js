import React, { useState } from 'react';
import styled from 'styled-components';
import { LongButton } from '../atoms/Buttons';

export default function UserAbout() {
  const [onModifyMode, setOnModifyMode] = useState(false);

  return (
    <>
      {onModifyMode ?
        <div style={{
          "display": "flex",
          "flexDirection": "column",
          "margin": "2rem auto",
          "width": "80%",
        }}>
          <textarea style={{
            "resize": "none",
            "height": "10rem",
          }}></textarea>
          <div style={{
            "display": "flex"
          }}>
            <AboutButton>편집완료</AboutButton>
            <AboutButton
              onClick={() => { setOnModifyMode(!onModifyMode) }}
            >편집취소</AboutButton>
          </div>
        </div>
        :
        <div style={{
          "display": "flex",
          "flexDirection": "column",
          "justifyContent": "center",
          "alignItems": "center",
          "marginTop": "1rem",
        }}>
          <AboutCard>아직 작성된 소개 글이 없습니다.</AboutCard>
          <LongButton
            style={{
              "width": "80%",
              "borderRadius": "0"
            }}
            onClick={() => {
              setOnModifyMode(!onModifyMode);
            }}

          >편집</LongButton>
        </div>
      }
    </>
  )
}

const AboutButton = styled(LongButton)`
  width : 50%;
  border-radius : 0;
`;


const AboutCard = styled.div`
  width : 80%;
  height : 6rem;
  word-break : break-all;
  margin : 0 auto;
  padding : 0.5rem;
  border : 1px solid black;
`;
