import React, { useState, useEffect } from 'react';
import styled from 'styled-components';

export default function CommentList(comments) {

  useEffect(() => {
  }, [comments])
  return (
    <>
      {comments ? Object.keys(comments).map((key) => {
        return <CommentCard {...comments[key]} key={key} />
      }) : <div>댓글이 없습니다.</div>}
    </>
  );
}

function CommentCard(comment) {
  const [onModifyCmt, setModifyCmt] = useState(false);
  const setModifyMode = () => {
    setModifyCmt(!onModifyCmt);
  }

  return (
    <CommentCards>
      {onModifyCmt ?
        <div className="modifyMode">
          <form>
            <textarea name="body">{comment.body}</textarea>
            <div>
              <button type="submit">수정완료</button>
              <button onClick={setModifyMode}>수정취소</button>
            </div>
          </form>
        </div>
        :
        <div className="comment">
          <div className="user">
            <div className="picture">사진</div>
            <div className="profile">
              <span className="name"> {comment.author} </span>
              <span className="date"> {comment.modifiedDate} </span>
            </div>
            <div className="cmtlist_btns">
              <button onClick={setModifyMode}>수정</button>
              <button>삭제</button>
            </div>
          </div>
          <div className="content">{comment.body}</div>
        </div>
      }
    </CommentCards>
  )
}

const BLACK_COLOR = "#222f3e";

const CommentCards = styled.div`
  margin: 0 auto;
  width: 45vw;
  color : ${BLACK_COLOR};
  display : flex;
  flex-direction : column;
  justify-content : center;
  align-items : center; 

  .modifyMode{
    border: 1px solid #dedede;
    padding: 10px 10px;
    border-radius: 5px;
    box-shadow: 2px 4px 3px #7f8fa6;
    margin-top: 10px;
    display: flex;
    flex-direction : column;
    justify-content : center;
    align-items : center;
    margin-bottom : 1rem;
  }
  
  .modifyMode textarea{
    overflow-x:hidden;
    resize: none;
    border-color: #dedede;
    border-radius: 5px;
    width: 43vw;
    height: 6rem;
  }
  .modifyMode div {
    width : 43vw;
    display : flex;
    justify-content : flex-end;
  }
  .modifyMode div button {
    margin-top : 8px;
    margin-left : 8px;
    background-color : white;
    padding : 5px 5px;
    border-radius : 5px;
    border-color: #dedede;
  }
  .comment {
    width: 43vw;
    border: 1px solid #dedede;
    padding: 10px 10px;
    border-radius: 5px;
    box-shadow: 2px 4px 3px #7f8fa6;
    margin-top: 10px;
    margin-bottom : 1rem;
  }
  .user {
    display: flex;
    align-items: flex-end;
  }
  
  .profile {
    margin-left: 5px;
    display: flex;
    flex-direction: column;
  }
  .name {
    font-size: 0.8rem;
  }
  .date {
    font-size: 0.5rem;
    color: #718093;
  }
  .picture {
    width: 50px;
    height: 50px;
    background-color: tomato;
    border-radius: 50px;
  }
  .content {
    margin-top: 1rem;
  }
  .cmtlist_btns {
    margin: 10px;
  }
  .cmtlist_btns button {
    border : none;
    background-color : white;
    font-size: 0.9rem;
    margin-left : .4rem;
    cursor : pointer;
  }
  
  @media screen and (max-width: 1024px) {
    width: 80vw;
  
    .picture {
      width: 30px;
      height: 30px;
      background-color: tomato;
      border-radius: 50px;
    }

    .modifyMode {
      width : 70vw;
    }
    .modifyMode textarea {
      width : 65vw;
    }
    .modifyMode div {
      width : 65vw;
    }    
    .comment {
      width : 70vw;
    }
    .picture {
      align-self : center;
    }

    .cmtlist_btns{
      margin : 0px;
      width: 6rem;
      display : flex;
      align-self : center;
    }

    .cmtlist_btns button{
      width : 3rem;
      font-size : 0.8rem;
    }

  }
`;