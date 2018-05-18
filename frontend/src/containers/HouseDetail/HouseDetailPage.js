import React, { Component } from 'react';
import fetch from 'node-fetch';

import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';
import PageTitle from '../../components/PageTitle/PageTitle';
import HouseDetailForm from '../../components/HouseDetailForm/HouseDetailForm';
import './HouseDetailPage.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';

export default class HouseDetailPage extends Component {

    constructor(props) {
        super(props);
        this.state = {
            house : [],
            price : [],
            btnMsg : '',
            isBalanceUpdated: false,
        };
        this.updateBalance = this.updateBalance.bind(this);
    }

    componentDidMount() {
        let url = 'http://172.30.48.190/api/houseDetails/' + this.props.match.params.id;
        fetch(url)
        .then((response) => response.json()).then((response) => {
            console.log(response);
            this.setState({
                house: response.data,
                price: response.data.price,
            });
        });
    }

    updateBalance() {
        this.setState({["isBalanceUpdated"]: true});
        setTimeout(() => {
            this.setState({["isBalanceUpdated"]: false});
        }, 500);
    }

    render() {
        return (
            <div className="houseDetailPage">
                <Header isBalanceUpdated={this.state.isBalanceUpdated} />
                <PageTitle title="مشخصات کامل ملک"/>

                <div className="main-content row">
                    <div className="col-xl-1 col-sm-12" />
                    <div className="col-xl-10 col-sm-12">
                        <HouseDetailForm
                            updateBalance={this.updateBalance}
                            house={this.state.house}
                            price={this.state.price}
                            parentName={this.props.match.params.parentName} />
                    </div>
                    <div className="col-xl-1 col-sm-12" />
                </div>

                <Footer/>
            </div>
        );
    }
}