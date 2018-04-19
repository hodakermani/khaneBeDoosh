import React, { Component }  from 'react';
import {Link} from 'react-router';
import './Header.css';
import 'font-awesome/css/font-awesome.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';

class Header extends Component {

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
            <nav className="header-container">
                <div className="row header-row">
                    <Link to={"home"} className="col-xl-6 col-sm-6 header-logo">
                        <img src="/images/logo.png" alt="خانه به دوش" />
                        <span>خانه به دوش</span>
                    </Link>

                    <div className="col-xl-6 desktop-header">
                        <div className="dropdown user-btn-header">
                            <div className="dropdown-content">
                                <p>{this.state.name}</p>
                                <div className="row">
                                    <div className="col-xl-6 col-sm-6 balance" id="name">
                                        <span>اعتبار</span>
                                    </div>
                                    <div className="col-xl-6 col-sm-6 balance" id="value">
                                        <span>{this.translateNum(String(this.state.balance))}</span>
                                        <span>&nbsp;</span>
                                        <span>تومان</span>
                                    </div>
                                </div>
                                <Link to={"addBalance"} className="shadowing">افزایش اعتبار</Link>
                            </div>
                            <a id="user-btn-header" href="">
                                <i className="fa fa-smile-o" />
                                <span>ناحیه کاربری</span>
                            </a>
                        </div>
                    </div>

                    <div className="col-sm-6 mobile-header">
                        <div className="username">
                            <span>{this.state.name}</span>
                        </div>
                        <Link to={"addBalance"} className="balance">
                            <span>اعتبار</span>
                            <span>&nbsp;</span>
                            <span>{this.translateNum(String(this.state.balance))}</span>
                            <span>&nbsp;</span>
                            <span>تومان</span>
                        </Link>
                    </div>
                </div>
            </nav>
        );
    }
};

export default Header;