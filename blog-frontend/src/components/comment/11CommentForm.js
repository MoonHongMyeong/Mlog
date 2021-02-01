import axios from 'axios';
import React from 'react'
import styled from 'styled-components';

class CommentForm extends React.Component {
  constructor() {
    super();
    this.state = {
      author: '',
      body: '',
    }
  }
  addComment = () => {
    const url = `/api/posts/${this.props.id}/comments`;
    return axios.post(url, this.state);
  }

  handleFormSubmit = (e) => {
    e.preventDefault();
    this.addComment().then(response => console.log(response)).then(alert('코멘트 등록이 성공했습니다.')).catch(error => console.log(error));
  }

  handleValueChange = (e) => {
    let nextState = {};
    nextState[e.target.name] = e.target.value;
    this.setState(nextState);
  }


  render() {
    return (
      <CommentFormContainer>
        <form onSubmit={this.handleFormSubmit}>
          <table>
            <tbody>
              <tr>
                <td><label htmlFor="author">작성자 : </label>
                  <input type="text" name="author" onChange={this.handleValueChange} /></td>
              </tr>
              <tr>
                <td>
                  <textarea name="body" id="comment" onChange={this.handleValueChange}></textarea>
                </td>
              </tr>
              <tr>
                <td>
                  <button type="submit">댓글등록</button>
                </td>
              </tr>
            </tbody>
          </table>
        </form>
      </CommentFormContainer>
    );
  }
}

const CommentFormContainer = styled.div`
margin : 0 auto;
width : 45vw;
padding : 10px 10px;
border-radius 5px;
border : 1px solid #dedede;
box-shadow : 2px 4px 3px #222f3e;
margin-top : 1rem;
margin-bottom : 1rem;

table{
  width : 100%;
  height : 100%;
}

input{
  border-color: #dedede;
}

input:focus{
  outline : none;
}

textarea {
  overflow-x: hidden;
  resize: none;
  border-color: #dedede;
  border-radius: 5px;
  width: 100%;
  height: 6rem;
}

textarea::placeholder {
  padding-left: 10px;
  padding-top: 10px;
  font-size: 15px;
}


.cmt_tool .cmtBtn {
  background-color: white;
  font-size: 16px;
  padding: 5px 5px;
  border-radius: 5px;
  border-color: #dedede;
}

.cmt_tool .cmtBtn:hover {
  background-color: black;
  color: white;
  cursor: pointer;
}

@media screen and (max-width:1024px){
  width : 75vw;
}

.cmt textarea {
  width : 90%;
}
`;

export default CommentForm;