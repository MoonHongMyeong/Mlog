import axios from 'axios';
import React from 'react';
import styled from 'styled-components';

const PostFormContainer = styled.div`
  width : 60vw;
  height : 690px;
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
    .btnContainer button,a {
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
      validation: {
        title: false,
        author: false,
        content: false,
      },
      valFile: false
    };
  }


  addPost = () => {
    const url = '/api/posts';
    const formData = new FormData();
    const config = {
      headers: {
        'Content-type': 'multipart/form-data'
      }
    }
    formData.append('title', this.state.title);
    formData.append('author', this.state.author);
    formData.append('image', this.state.file);
    formData.append('content', this.state.content);

    return axios.post(url, formData, config);
  }

  handleFormSubmit = (e) => {
    e.preventDefault();
    console.log(this.state);
    if (!this.state.title || !this.state.validation.author || !this.state.validation.content) {
      alert("입력하지 않은 곳이 있습니다. \n다시 입력해주세요.");
    } else {
      this.addPost()
        .then((response) => {
          alert('게시글 등록이 완료되었습니다.');
          console.log(response.data);
          window.location.href = `/api/posts/${response.data}`;
        })
        .catch(Error => console.log(Error));
    }
  }


  handleFileChange = (e) => {
    this.setState({
      file: e.target.files[0],
      fileName: e.target.value,
    });
    this.setState({ valFile: true });
  }

  handleValueChange = (e) => {
    const { name, value } = e.target;
    let validation = this.state.validation;
    switch (name) {
      case 'title':
        validation.title = value.length < 2 ? false : true;
        break;
      case 'author':
        validation.author = value.length < 2 ? false : true;
        break;
      case 'content':
        validation.content = value.length < 2 ? false : true;
        break;
      default:
        break;
    }

    this.setState({ validation, [name]: value });
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
                    placeholder={this.state.validation.title ? "" : '제목을 입력해주세요'}
                    onChange={this.handleValueChange} /></td>
              </tr>
              <tr>
                <td className="input_name">작성자</td>
                <td className="input">
                  <input
                    type="text"
                    name="author"
                    value={this.state.author}
                    placeholder={this.state.validation.author ? "" : '작성자를 입력해주세요'}
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
                    accept="image/*"
                    onChange={this.handleFileChange} /> <p style={{ "fontSize": "0.7rem  " }}>파일을 추가하지 않으면 기본값으로 설정됩니다.</p></td>
              </tr>
              <tr>
                <td className="input_name content">내용</td>
                <td className="input content">
                  <textarea
                    name="content"
                    spellCheck="false"
                    value={this.state.content}
                    placeholder={this.state.validation.content ? "" : '내용을 입력해주세요'}
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