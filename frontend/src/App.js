import React, { Component } from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import UrlShorten from "./UrlShorten";
import PreRedirectPage from "./PreRedirectPage";

class App extends Component {
  render() {
    return (
        <Router>
            <Switch>
                <Route path='/' exact={true} component={UrlShorten}/>
                <Route path='/r/:shortUrl' exact={true} component={PreRedirectPage}/>
            </Switch>
        </Router>
    )
  }
}

export default App;