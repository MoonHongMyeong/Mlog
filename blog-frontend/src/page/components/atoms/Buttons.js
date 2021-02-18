import styled from 'styled-components';

export const Button = styled.button`
    width : 5rem;
    height : 2rem;
    border : none;
    color : ${props => props.fontColor || 'white'};
    border-radius : 5px;
    font-weight : 500;
    font-size: .8rem;
    background : ${props => props.color || 'black'};
    cursor:pointer;
    &:focus{
      outline:none;
    }
    &:hover{
      opacity : 0.7;
    }
    @media screen and (min-width: 1024px){

    }
`;

export const LongButton = styled(Button)`
  width : 10rem;
  height : 2.5rem;
`;

export const LoginButton = styled(Button)`
  border-radius : 2em;
  background-color:#4b4b4b;
  &:hover {
    background-color : black;
    text-decoration : none;
  }
`;

export const LogoutButton = styled(LoginButton)`
  background-color : #e3efe2;
  color :  #3d3d3d;
  font-weight : 600;
  &:hover{
    color : #e3efe2;
  }
`;

export const FormButton = styled(Button)`
  border-radius : 1rem;

  &:hover{
    opacity : 1;
    text-decoration : underline;
  }
`;

export const CommentButton = styled.button`
  background-color : white;
  border : none;
  cursor : pointer;
  margin-right : .45rem;
  &:hover{
    text-decoration : underline;
  }
  &:focus{
    outline:none;
  }
  
`;
