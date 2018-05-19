import React, { Component } from 'react';
import {Link} from 'react-router-dom';
import './Dropdown.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';
import 'font-awesome/css/font-awesome.css';

class Dropdown extends Component {

    constructor(props) {
        super(props);
        this.state = {
            balance: '',
        };
        this.translateNum =  this.translateNum.bind(this);
    }

    componentDidMount() {
        let url = 'http://172.30.48.139:4000/secure/api/getBalance';
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

            this.setState({
                balance: response.Balance,
                name: response.Name
            });
        });
    }

    translateNum(m) {
        var num=JSON.parse('{"0":"۰","1":"۱","2":"۲","3":"۳","4":"۴","5":"۵","6":"۶","7":"۷","8":"۸","9":"۹"}');
        return m.replace(/./g,function(c){
            return (typeof num[c]==="undefined")?
                ((/\d+/.test(c))?c:''):
                num[c];
        })
    }

    render() {
        return (
            <div className="dropdown">
                <a id="user-btn-home" href="">
                    <i className="fa fa-smile-o"/>
                    <span>ناحیه کاربری</span>
                </a>
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
                    <Link to={"/addBalance"} className="shadowing">افزایش اعتبار</Link>
                </div>
            </div>
        );
    }
};

export default Dropdown;