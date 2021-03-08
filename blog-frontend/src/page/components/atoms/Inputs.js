import styled from 'styled-components';

export const TitleInput = styled.input`
  width : 99.28%;
  margin : 0 auto;
  height : 3rem;
  border : none;
  font-size : 2rem;
  font-weight : 700;
  border-bottom : 4px solid #dedede;
  &:focus{
    outline : none;
  }
  &::placeholder{
    opacity : 0.7;
  }
`;

export const FormTextarea = styled.textarea`
  height : ${props => props.height};
  resize: none;
  width: 99.28%;
  margin: 2rem 0 auto;
  border: none;
  font-size : 1.2rem;

  &:focus{
    outline : none;
  }
`;

export const CommentTextarea = styled.textarea`
  white-space : pre;
  height : 7rem;
  resize : vertical;
  width : 98%;
  border : none;
  &:focus{
    outline : none;
  }
`;

export const SearchInput = styled.input`
margin: 2rem;
width : 50%;
height : 3rem;
border : none;
border-bottom : 1px solid black;

&:focus {
  outline : none;
}

@media screen and (max-width : 990px){
  width : 70%;
}
@media screen and (max-width : 500px){
  width : 90%;
}

`;