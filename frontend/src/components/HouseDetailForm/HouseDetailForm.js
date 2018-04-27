import React, { Component } from 'react';

import './HouseDetailForm.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';

const reactStringReplace = require('react-string-replace');


class HouseDetailForm extends Component {

    constructor(props) {
        super(props);
        this.state = {
            btnMsg: 'دریافت شماره مالک/مشاور',
            phoneVisibility : false,
        };
        this.handleChange = this.handleChange.bind(this);
        this.translateNum =  this.translateNum.bind(this);
    }

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value,
        });
    }

    handleClick = () =>{
        let url = '/api/houseDetailsGetPhone?id=' + this.props.house.id + '&parentName=' + this.props.parentName;
        fetch(url)
            .then(response => response.json()).then((response) => {
            this.setState({
                btnMsg: response.btnMsg,
                phoneVisibility: response.success,
                currUsername: response.username
            });
            this.props.updateBalance();
        });
    };

    translateNum(m) {
        var num=JSON.parse('{"0":"۰","1":"۱","2":"۲","3":"۳","4":"۴","5":"۵","6":"۶","7":"۷","8":"۸","9":"۹"}');
        return m.replace(/./g,function(c){
            return (typeof num[c]==="undefined")?
                ((/\d+/.test(c))?c:''):
                num[c];
        })
    }

    render() {
        const content = this.props.house.phone;
        const notRequested = 'دریافت شماره مالک/مشاور';
        const notEnoughBalance = 'اعتبار شما برای دریافت شماره مالک/مشاور کافی نیست';
        const isCurrUser = this.props.parentName === this.state.currUsername;

        return (
            <div className="houseDetailForm row">

                <div className="col-xl-4 col-sm-12 house-info">
                    <div className="buildingType">
                        { (this.props.house.dealType === "خرید" || this.props.house.dealType === 0) ? (
                            <span className="background-purple">فروش</span>

                        ) : (
                            <span className="background-pink">رهن و اجاره</span>
                        )}
                    </div>

                    <div className="row">
                        <div className="col-xl-5 col-sm-6">
                            <span>شماره مالک/مشاور</span>
                        </div>
                        <div className="col-xl-7 col-sm-6 phone">
                            { (this.state.phoneVisibility === false) ? (
                                <span>
                                    {reactStringReplace(content, /(-.*-)/g, (match, i) => (
                                        <span key={i}>**</span>
                                    ))}
                                </span>
                            ) : (
                                <span>
                                    {this.translateNum(String(this.props.house.phone))}
                                </span>
                            )}

                        </div>
                    </div>

                    <div className="row">
                        <div className="col-xl-5 col-sm-6">
                            <span>نوع ساختمان</span>
                        </div>
                        <div className="col-xl-7 col-sm-6">
                            <span>{this.props.house.buildingType}</span>
                        </div>
                    </div>

                    { (this.props.house.dealType === "خرید" || this.props.house.dealType === 0) ? (
                        <div className="row">
                            <div className="col-xl-5 col-sm-6">
                                <span>قیمت</span>
                            </div>
                            <div className="col-xl-7 col-sm-6">
                                <span>{this.translateNum(String(this.props.price.sellPrice))}</span><span>&nbsp;</span>‌<span>تومان</span>
                            </div>
                        </div>
                    ) : (
                        <div>
                            <div className="price row">
                                <div className="col-xl-5 col-sm-6">
                                    <span>رهن</span>
                                </div>
                                <div className="col-xl-7 col-sm-6">
                                    <span>{this.translateNum(String(this.props.price.basePrice))}</span><span>&nbsp;</span>‌<span>تومان</span>
                                </div>
                            </div>
                            <div className="price row">
                                <div className="col-xl-5 col-sm-6">
                                    <span>اجاره</span>
                                </div>
                                <div className="col-xl-7 col-sm-6">
                                    <span>{this.translateNum(String(this.props.price.rentPrice))}</span><span>&nbsp;</span>‌<span>تومان</span>
                                </div>
                            </div>
                        </div>
                    )}

                    <div className="row">
                        <div className="col-xl-5 col-sm-6">
                            <span>آدرس</span>
                        </div>
                        <div className="col-xl-7 col-sm-6">
                            <span>{this.props.house.address}</span>
                        </div>
                    </div>

                    <div className="row">
                        <div className="col-xl-5 col-sm-6">
                            <span>متراژ</span>
                        </div>
                        <div className="col-xl-7 col-sm-6">
                            <span>{this.translateNum(String(this.props.house.area))}</span>
                            <span>&nbsp;</span>
                            <span>متر مربع</span>
                        </div>
                    </div>

                    <div className="row">
                        <div className="col-xl-5 col-sm-6">
                            <span>توضیحات</span>
                        </div>
                        <div className="col-xl-7 col-sm-6">
                            <span>{this.props.house.description}</span>
                        </div>
                    </div>
                </div>

                <div className="col-xl-8 col-sm-12">
                    <img src={this.props.house.imageURL} id="house-img" alt="House" />
                    <a id="get-house-phone"
                       className={(this.state.btnMsg === notRequested && !isCurrUser ? 'balance-not-requested' :
                            this.state.btnMsg === notEnoughBalance && !isCurrUser ? 'balance-not-enough' :
                           'balance-was-enough' )}
                       onClick={this.handleClick}>
                        {this.state.btnMsg}
                    </a>
                </div>

            </div>
        );
    }

}

export default HouseDetailForm;
