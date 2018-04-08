import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Router, Route, browserHistory } from 'react-router';
import Home from './containers/Home/HomePage';
import AddBalance from './containers/AddBalance/AddBalancePage';
import AddHouse from './containers/AddHouse/AddHousePage';
import HouseDetail from './containers/HouseDetail/HouseDetailPage';
import Search from './containers/Search/SearchPage';
import './assets/styles/font.css';
import './index.css';

class App extends Component {
    render() {
        return(
            <Router history={browserHistory}>
                <Route path="addBalance" component={AddBalance} />
                <Route path="addHouse" component={AddHouse} />
                <Route path="houseDetail/:id/:parentName" component={HouseDetail} />
                <Route path="search/:maxPrice/:minArea/:buildingType/:dealType" component={Search} />
                <Route path="home" component={Home} />
            </Router>
        );
    }
}

ReactDOM.render(<App/>, document.getElementById('root'));