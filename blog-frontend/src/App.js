import React, { useState, useRef } from 'react'
import { BrowserRouter as Router, Link, Route, Switch } from 'react-router-dom';
import PostList from './components/post/PostList';
import PostForm from './components/post/PostForm';
import PostView from './components/post/PostView';

function App() {
  const [isMenuOpened, setMenuOpen] = useState(true);
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

  return (
    <Router>
      <button className="NavBtn" onClick={toggleMenu}>b</button>
      <nav className="navigation" ref={navRef}>
        <form>
          <div>
            <input type="text" placeholder="Search" /><button type="submit" className="search"><i className="fas fa-search"></i></button>
          </div>
        </form>
        <ul>
          <li className="navLink"><Link to="/">최신 포스트</Link></li>
          <li className="navLink"><Link to="/">인기 포스트</Link></li>
          <li className="navLink"><Link to="/">글쓰기</Link></li>
        </ul>
      </nav>
      <Switch>
        <Route exact path="/" component={PostList} />
        <Route exact path="/api/posts" component={PostList} />
        <Route exact path="/api/posts/form" component={PostForm} />
        <Route exact path="/api/posts/:postId" component={PostView} />
      </Switch>
    </Router>
  );
}

export default App;
