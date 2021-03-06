import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Route } from 'react-router-dom';
import Home from './containers/Home/HomePage';
import AddBalance from './containers/AddBalance/AddBalancePage';
import AddHouse from './containers/AddHouse/AddHousePage';
import HouseDetail from './containers/HouseDetail/HouseDetailPage';
import Search from './containers/Search/SearchPage';
import Login from './containers/Login/LoginPage';

import './assets/styles/font.css';
import './index.css';

class App extends Component {
    render() {
        return(

            <BrowserRouter>
                <div className="maxHeight">
                    <Route path="/addBalance" component={AddBalance} />
                    <Route path="/addHouse" component={AddHouse} />
                    <Route path="/houseDetail/:id" component={HouseDetail} />
                    <Route path="/search/:maxPrice/:minArea/:buildingType/:dealType" component={Search} />
                    <Route path="/home" component={Home} />
                    <Route path="/login" component={Login} />
                </div>
            </BrowserRouter>
        );
    }
}

ReactDOM.render(<App/>, document.getElementById('root'));