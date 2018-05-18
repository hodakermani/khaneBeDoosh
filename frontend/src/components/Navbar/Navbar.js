import React, { Component } from 'react';
import './Navbar.css';
import { Link } from 'react-router-dom';
import Dropdown from '../Dropdown/Dropdown';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';

class Navbar extends Component {

    constructor(props) {
        super(props);
        this.state = {
            balance: '',
            userLoggedIn: false,
        };
        this.translateNum =  this.translateNum.bind(this);
    }

    translateNum(m) {
        var num=JSON.parse('{"0":"۰","1":"۱","2":"۲","3":"۳","4":"۴","5":"۵","6":"۶","7":"۷","8":"۸","9":"۹"}');
        return m.replace(/./g,function(c){
            return (typeof num[c]==="undefined")?
                ((/\d+/.test(c))?c:''):
                num[c];
        })
    }

    componentDidMount() {
        let url = 'http://172.30.48.190:4000/secure/api/getBalance';
        let header = 'Bearer ' + localStorage.getItem("loginToken");
        console.log("=============================");
        console.log(header);
        var obj = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': header,
            }
        };
        fetch(url, obj)
            .then(response => response.json()).then((response) => {
            console.log(response);

            if (response.status === 401)
                this.setState({ userLoggedIn: false });
            else {
                this.setState({
                    userLoggedIn: true,
                    balance: response.Balance,
                    name: response.Name
                });
            }
        });
    }

    render() {
        return (
            <nav className="navBar">
                <div className={this.state.userLoggedIn ? 'doDisplay' : 'doNotDisplay'}>
                    <div className="row desktop-header">
                        <div className="col-xl-12 col-sm-12">
                            <Dropdown/>
                        </div>
                    </div>
                    <div className="mobile-header">
                        <Link to={"/addBalance"} className="balance">
                            <span>اعتبار</span>
                            <span>&nbsp;</span>
                            <span>{this.translateNum(String(this.state.balance))}</span>
                            <span>&nbsp;</span>
                            <span>تومان</span>
                        </Link>
                        <div className="username">
                            <span>{this.state.name}</span>
                        </div>
                    </div>
                </div>

                <div className="logo">
                    <img src="/images/logo.png" alt="خانه به دوش" />
                    <p>خانه به دوش</p>
                </div>
            </nav>
        );
    }
};

export default Navbar;