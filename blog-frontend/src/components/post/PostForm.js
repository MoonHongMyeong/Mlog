import axios from 'axios';
import React from 'react';
import styled from 'styled-components';

const PostFormContainer = styled.div`
  width : 60vw;
  height : 82.7vh;
  margin : 0 auto;
  margin-top : 12vh;

  table {
    margin: 0 auto;
    margin-top: 3rem;
  }
  
  .input_name {
    width: 10vw;
    text-align: center;
    background-color: black;
    color: white;
  }
  .input {
    width: 30vw;
    padding: 10px 10px;
  }
  
  .input input {
    width: 66.6%;
    height: 1.5rem;
  }
  
  .input textarea {
    resize: none;
    width: 30vw;
    height: 20rem;
  }
  
  .btn {
    margin-top: 2rem;
  }
  
  .btnContainer {
    margin: 0 auto;
    width: 30vw;
    display: flex;
    justify-content: center;
  }
  
  .btnContainer button,a {
    width: 80px;
    border: none;
    height: 30px;
    border-radius: 10px;
    font-weight: 0;
    font-size : 0.9rem;
    text-align : center;
    margin: 10px;
    display : flex;
    justify-content : center;
    align-items : center;
  }
  
  h1 {
    margin-left: 10vw;
    margin-bottom: 1rem;
  }
  
  #preview {
    padding-top: 3rem;
    margin: 0 auto;
    margin-bottom : 2rem;
    width: 40vw;
  }
  
  .preview_btn {
    background-color: #487eb0;
    color: #f5f6fa;
  }
  .submit_btn {
    background-color: #8c7ae6;
    color: #f5f6fa;
    cursor: pointer;
  }
  .cancel_btn {
    text-decoration : none;
    background-color: #c23616;
    color: #f5f6fa;
  }
  @media screen and (max-width: 1024px) {
    width: 70vw;
    
    .input_name {
      width: 18vw;
      text-align: center;
      background-color: black;
      color: white;
      font-size : 0.8rem;
    }
    .input {
      width: 56vw;
      padding: 8px 8px;
    }
    .input input {
      width: 40vw;
      height: 1.5rem;
    }
    .input textarea {
      margin-top : 10px;
      resize: none;
      width: 50vw;
      height: 20rem;
    }
  }
  
  @media screen and (max-width: 770px) {
    .input input{
      height : 1.3rem;
    }
    .input textarea{
      margin : 0;
      height : 20rem;
    }
    .content{
      height : 22rem;
    }
    .btnContainer {
      display: flex;
      flex-direction: column;
    }
    .btnContainer button {
      width: 100%;
    }
    .input_name {
      font-size: 0.7rem;
    }
  }
  
  @media scrren and (max-width : 500px){
    .input {
      width : 30vw;
    }
    .input input {
      margin : 0;
      width: 10vw;
      height: 1.3rem;
    }

    #preview {
      width : 80vw;
    }
  }
`;


class PostForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      title: '',
      author: '',
      content: '',
      file: null,
      fileName: '',
      text: ''
    };
  }


  addPost = () => {
    const url = '/api/posts';
    const formData = new FormData();
    formData.append('title', this.state.title);
    formData.append('author', this.state.author);//회원기능 만들면 회원 받는 로직 v2에서 만들거임 1은 배포용이라서 개인정보보호 신경쓰기 싫음
    formData.append('image', this.state.file);
    formData.append('content', this.state.content);
    const config = {
      headers: {
        'Content-type': 'multipart/form-data'
      }
    }
    return axios.post(url, formData, config);
  }

  handleFormSubmit = (e) => {
    e.preventDefault();
    this.addPost()
      .then((response) => {
        alert('게시글 등록이 완료되었습니다.');
        console.log(response.data);
        window.location.href = `/api/posts/${response.data}`;
      })
      .catch(Error => console.log(Error));
  }

  handleFileChange = (e) => {
    this.setState({
      file: e.target.files[0],
      fileName: e.target.value
    })
  }

  handleValueChange = (e) => {
    let nextState = {};
    nextState[e.target.name] = e.target.value;
    this.setState(nextState);
  }



  render() {
    return (
      <PostFormContainer>
        <form method="post"
          encType="multipart/form-data"
          onSubmit={this.handleFormSubmit}>
          <table>
            <tbody>
              <tr>
                <td className="input_name">제목</td>
                <td className="input">
                  <input
                    type="text" name="title"
                    value={this.state.title}
                    onChange={this.handleValueChange} /></td>
              </tr>
              <tr>
                <td className="input_name">작성자</td>
                <td className="input">
                  <input
                    type="text"
                    name="author"
                    value={this.state.author}
                    onChange={this.handleValueChange} /></td>
              </tr>
              <tr>
                <td className="input_name">썸네일</td>
                <td className="input">
                  <input
                    type="file"
                    name="image"
                    image={this.state.file}
                    value={this.state.fileName}
                    onChange={this.handleFileChange} /></td>
              </tr>
              <tr>
                <td className="input_name content">내용</td>
                <td className="input content">
                  <textarea
                    name="content"
                    value={this.state.content}
                    onChange={this.handleValueChange}></textarea></td>
              </tr>
            </tbody>
          </table>

          <div className="btn">
            <div className="btnContainer">
              <button type="submit" className="submit_btn">등록하기</button>
              <a href="/" className="cancel_btn">취소하기</a>
            </div>
          </div>
        </form>
      </PostFormContainer>
    )
  }
}

export default PostForm;