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
            isBalanceUpdated: false,
        };
        this.updateBalance = this.updateBalance.bind(this);
    }

    componentDidMount() {
        console.log("id = " + this.props.match.params.id);

        let url = 'http://172.20.10.4:4000/api/houseDetails/'+ this.props.match.params.id;
        console.log(url);

        var obj = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        };

        fetch(url, obj).then((response) => response.json()).then((response) => {
            console.log("here ^_^");
            console.log(response.data);
            this.setState({
                house: response.data,
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
                            parentName={this.props.match.params.parentName} />
                    </div>
                    <div className="col-xl-1 col-sm-12" />
                </div>

                <Footer/>
            </div>
        );
    }
}