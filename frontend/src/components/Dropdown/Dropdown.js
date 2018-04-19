import React, { Component } from 'react';
import {Link} from 'react-router';
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
                    <Link to={"addBalance"} className="shadowing">افزایش اعتبار</Link>
                </div>
            </div>
        );
    }
};

export default Dropdown;