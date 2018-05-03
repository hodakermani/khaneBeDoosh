import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import LoginForm from '../../components/LoginForm/LoginForm';
import './LoginPage.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';
// import Footer from "../../components/Footer/Footer";

export default class Login extends Component {
    constructor(props){
        super(props);
        this.state = {
          isLoggedIn: false
        };
        this.setIsLoggedIn = this.setIsLoggedIn.bind(this);
    }
    setIsLoggedIn(isLoggedIn){
        this.setState({isLoggedIn});
    }
    render() {
        return (
            <div className="loginPage maxHeight">
                {this.state.isLoggedIn && <Redirect to="/home" />}
                <div className="main-content">
                    <div className="row">
                        <div className="col-xl-2 col-md-12 col-sm-12" />
                        <div className="col-xl-8 col-md-12 col-sm-12">
                            <LoginForm className="loginForm" loginCallBack={this.setIsLoggedIn}/>
                        </div>
                        <div className="col-xl-2 col-md-12 col-sm-12" />
                    </div>
                </div>
            </div>
        );
    }
}