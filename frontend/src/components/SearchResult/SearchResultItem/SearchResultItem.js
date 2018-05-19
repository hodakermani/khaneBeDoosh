import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import './SearchResultItem.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';

class SearchResultItem extends Component {

    constructor(props) {
        super(props);
        this.state = {
            house: [],
        }
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
        this.setState({
            house: this.props.house,
        });
    }

    render() {
        return (
            <Link to={"/houseDetail/" + this.state.house.ID}>
                <div className="searchResultItem">
                    <div className="image-container">
                        <img src={this.props.house.ImageURL} alt="خانه" />

                        { (this.props.house.DealType === "خرید" || this.props.house.DealType === 0) ? (
                            <div className="image-label background-purple">
                                <span>فروش</span>
                            </div>
                        ) : (
                            <div className="image-label background-pink">
                                <span>رهن و اجاره</span>
                            </div>
                        )}
                    </div>

                    <div className="info-container">
                        <div className="row">
                            <div className="col-6 text-align-right">
                                <span>{this.translateNum(String(this.props.house.Area))}</span><span>&nbsp;</span><span>متر مربع</span>
                            </div>
                            <div className="col-6 text-align-center">
                                <i className="fa fa-map-marker text-pink" />
                                <span className="location">{this.props.house.Address}</span>
                            </div>
                        </div>

                        { (this.props.house.DealType === "خرید" || this.props.house.DealType === 0) ? (
                            <div className="row text-align-right">
                                <div className="col-6">
                                    <span className="contract-type">قیمت</span><span>{this.translateNum(String(this.props.house.SellPrice))}</span><span>&nbsp;</span>‌<span className="toman">تومان</span>
                                </div>
                                <div className="col-6 text-align-center" />
                            </div>
                        ) : (
                            <div className="row text-align-right">
                                <div className="col-6">
                                    <span className="contract-type">رهن</span><span>{this.translateNum(String(this.props.house.BasePrice))}</span><span>&nbsp;</span>‌<span className="toman">تومان</span>
                                </div>
                                <div className="col-6 text-align-center">
                                    <span className="contract-type">اجاره</span><span>{this.translateNum(String(this.props.house.RentPrice))}</span><span>&nbsp;</span>‌<span className="toman">تومان</span>
                                </div>
                            </div>
                        )}

                    </div>
                </div>
            </Link>
        );
    }

}

export default SearchResultItem;
