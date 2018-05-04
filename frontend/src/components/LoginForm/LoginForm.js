import React, { Component } from 'react';
import Cookies from 'universal-cookie';
import './LoginForm.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';

// const cookies = new Cookies();

class LoginForm extends Component {

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            credentialsOK: false,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleClick = this.handleClick.bind(this);
    }

    handleChange(event) {
        this.setState({[event.target.name]: event.target.value});
    }

    handleClick = async () =>{
        let url = '/auth/login';
        var obj = {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Origin': '',
            },
            body: JSON.stringify({
                'username': this.state.username,
                'password': this.state.password,
            })
        };
        fetch(url, obj)
            .then(response => response.json()).then((response) => {
            console.log(response);
            this.props.loginCallBack(response.success);
            localStorage.setItem("loginToken", response.msg);
        });
    };

    render() {
        return (
            <div className="loginForm">
                <div className="container">
                    <img className="logo" src="/images/logo.png" alt="خانه به دوش" />
                    <p className="loginText">خانه به دوش</p>
                    <input className="form-control in" name="username" placeholder="نام کاربری" onChange={this.handleChange} />
                    <input className="form-control in" name="password" type="text" placeholder="رمز عبور" onChange={this.handleChange} />
                    <a onClick={this.handleClick} className="submit btn">ورود</a>
                </div>
            </div>
        );
    }
}

export default LoginForm;