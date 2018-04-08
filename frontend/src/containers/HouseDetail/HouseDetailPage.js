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
        };
    }

    componentDidMount() {
        let url = '/api/houseDetails?id=' + this.props.params.id + '&parentName=' + this.props.params.parentName;
        fetch(url)
        .then((response) => response.json()).then((response) => {
            console.log(response);
            this.setState({
                house: response.data,
                price: response.data.price,
                btnMsg: response.btnMsg,
            });
        });
    }

    render() {
        return (
            <div className="houseDetailPage">
                <Header />
                <PageTitle title="مشخصات کامل ملک"/>

                <div className="main-content row">
                    <div className="col-xl-1 col-sm-12" />
                    <div className="col-xl-10 col-sm-12">
                        <HouseDetailForm
                            house={this.state.house}
                            price={this.state.price}
                            btnMsg={this.state.btnMsg}
                            parentName={this.props.params.parentName} />
                    </div>
                    <div className="col-xl-1 col-sm-12" />
                </div>

                <Footer/>
            </div>
        );
    }
}