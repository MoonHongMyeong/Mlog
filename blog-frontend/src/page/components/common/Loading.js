import React from 'react';
import styled from '@emotion/styled';

export default function Loading() {
  return (
    <Container>
      <i className="fas fa-spinner"></i>
      <span>Loading...</span>
    </Container>
  )
}

const Container = styled.div`
  width : 100vw;
  height : 90vh;
  display : flex;
  flex-direction : column;
  justify-content : center;
  align-items : center;
  font-size : 4rem;
  .fa-spinner{
    animation : rotation 2s 1s infinite linear;
    -webkit-animation : rotation 2s 1s infinite linear;
    -moz-animation : rotation 2s 1s infinite linear;
    -ms-animation : rotation 2s 1s infinite linear;
    -o-animation : rotation 2s 1s infinite linear;
  }
  span{
    margin-top : 5px;
    font-size : 2rem;
    animation : fadeOut 3s 2s infinite;
    -webkit-animation : fadeOut 3s 2s infinite;
    -moz-animation : fadeOut 3s 2s infinite;
    -ms-animation : fadeOut 3s 2s infinite;
    -o-animation : fadeOut 3s 2s infinite;
  }
  
  @keyframes rotation{
    from{
      transform : rotate(0deg);
    }
    to{
      transform : rotate(360deg);
    }
  }
  @-webkit-keyframes rotation{
    from{
      transform : rotate(0deg);
    }
    to{
      transform : rotate(360deg);
    }
  }
  @-moz-keyframes rotation{
    from{
      transform : rotate(0deg);
    }
    to{
      transform : rotate(360deg);
    }
  }
  @-o-keyframes rotation{
    from{
      transform : rotate(0deg);
    }
    to{
      transform : rotate(360deg);
    }
  }
  @-ms-keyframes rotation{
    from{
      transform : rotate(0deg);
    }
    to{
      transform : rotate(360deg);
    }
  }
  @keyframes fadeOut{
    from{
      opacity : 0;
    }
    to{
      opacity : 1;
    }
  }
  @-webkit-keyframes fadeOut{
    from{
      opacity : 0;
    }
    to{
      opacity : 1;
    }
  }
  @-moz-keyframes fadeOut{
    from{
      opacity : 0;
    }
    to{
      opacity : 1;
    }
  }
  @-ms-keyframes fadeOut{
    from{
      opacity : 0;
    }
    to{
      opacity : 1;
    }
  }
  @-o-keyframes fadeOut{
    from{
      opacity : 0;
    }
    to{
      opacity : 1;
    }
  }
`;
