import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
// import Loading from './page/components/common/Loading';
import PostList from './page/PostList';
import Header from './page/components/common/Header';
import PostForm from './page/PostForm';
import PostView from './page/PostView';
import UserPage from './page/UserPage';

function App() {
  return (
    <Router>
      <>
        <Header />
        <Switch>
          <Route exact path="/" component={PostList} />
          <Route exact path="/form" component={PostForm} />
          <Route exact path="/post" component={PostView} />
          <Route exact path="/user" component={UserPage} />
        </Switch>
      </>
    </Router>

  );
}

export default App;
