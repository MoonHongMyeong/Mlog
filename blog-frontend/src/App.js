import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import PostList from './page/PostList';
import Header from './page/components/common/Header';
import PostForm from './page/PostForm';
import PostView from './page/PostView';
import UserPage from './page/UserPage';
import SearchedPost from './page/SearchedPost';
import PopPosts from './page/PopPosts';

function App() {


  return (
    <Router>
      <>
        <Header />
        <Switch>
          <Route exact path="/" component={PostList} />
          <Route exact path="/api/v2/posts" component={PostList} />
          <Route exact path="/api/v2/popPosts" component={PopPosts} />
          <Route exact path="/api/v2/searchedPosts" component={SearchedPost} />
          <Route exact path="/api/v2/write" component={PostForm} />
          <Route exact path="/api/v2/posts/:postId" component={PostView} />
          <Route exact path="/api/v2/user/:userId" component={UserPage} />
        </Switch>
      </>
    </Router>

  );
}

export default App;
