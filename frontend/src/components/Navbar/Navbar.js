import React, { Component } from 'react';
import './Navbar.css';
import { Link } from 'react-router';
import Dropdown from '../Dropdown/Dropdown';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';

class Navbar extends Component {

    constructor(props) {
        super(props);
        this.state = {
            balance: '',
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
        let url = '/api/getBalance';
        fetch(url)
            .then(response => response.json()).then((response) => {
            console.log(response);

            this.setState({
                balance: response.currentBalance,
                name: response.name
            });
        });
    }

    render() {
        return (
            <nav className="nav-bar">

                <div className="row desktop-header">
                    <div className="col-xl-12 col-sm-12">
                        <Dropdown/>
                    </div>
                </div>

                <div className="mobile-header">
                    <Link to={"addBalance"} className="balance">
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

                <div className="logo">
                    <img src="/images/logo.png" alt="خانه به دوش" />
                    <p>خانه به دوش</p>
                </div>
            </nav>
        );
    }
};

export default Navbar;