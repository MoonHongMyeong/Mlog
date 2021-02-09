import React, { useState, useRef } from 'react'
import { BrowserRouter as Router, Link, Route, Switch } from 'react-router-dom';
import axios from 'axios';
import PostList from './components/post/PostList';
import PostForm from './components/post/PostForm';
import PostView from './components/post/PostView';
import SearchList from './components/post/SearchList';

function App() {
  const [isMenuOpened, setMenuOpen] = useState(true);
  const [Search, setSearch] = useState("");
  const [SearchedPosts, setSearchedPosts] = useState([]);
  const navRef = useRef();
  const toggleMenu = () => {
    if (isMenuOpened) {
      navRef.current.style.transform = 'translateX(300px)';
      navRef.current.style.transition = 'transform 0.5s ease-in-out';
    } else {
      navRef.current.style.transform = 'translateX(0px)';
      navRef.current.style.transition = 'transform 0.5 ease-in-out';
    }
    setMenuOpen(!isMenuOpened);
  }

  const searchValueChange = (e) => {
    setSearch(e.currentTarget.value);
  }

  const handleTitleSearch = (e) => {
    axios.get('/api/posts/search', { params: { search: Search } })
      .then(response => {
        setSearchedPosts(response.data);
        setSearch("");
      });
  }

  return (
    <Router>
      <button className="NavBtn" onClick={toggleMenu}>b</button>
      <nav className="navigation" ref={navRef}>
        <form>
          <div>
            <input type="text" placeholder="Search" onKeyPress={(e) => e.preventDefault()} value={Search} onChange={searchValueChange} name="search" style={{ "color": "#CCCDC1" }} />
            <Link to="/api/posts/search" onClick={handleTitleSearch}><i className="fas fa-search"></i></Link>
          </div>
        </form>
        <ul>
          <li className="navLink"><Link to="/">포스트 리스트</Link></li>
          <li className="navLink"><Link to="/api/posts/form">글쓰기</Link></li>
        </ul>
      </nav>
      <Switch>
        <Route exact path="/" component={PostList} />
        <Route exact path="/api/posts" component={PostList} />
        <Route exact path="/api/posts/search" render={() => <SearchList searchedPosts={SearchedPosts} />} />
        <Route exact path="/api/posts/form" component={PostForm} />
        <Route exact path="/api/posts/:postId" component={PostView} />

      </Switch>
      <footer>
        <p>
          <span>#Contact : </span>
          <span
          ><a href="mailto:moonhongmyeong@gmail.com"
          ><i className="fas fa-envelope"></i></a
            ></span>
          <span> , </span>
          <span
          ><a href="https://github.com/moon4368"
          ><i className="fab fa-github-square"></i></a
            ></span>
        </p>
        <p>
          <span>Design by Moon Hong Myeong ⓒ 2021</span>
        </p>
      </footer>
    </Router>
  );
}

export default App;
