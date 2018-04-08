import React, { Component } from 'react';

import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';
import PageTitle from '../../components/PageTitle/PageTitle';
import AddBalanceForm from '../../components/AddBalanceForm/AddBalanceForm';

import './AddBalancePage.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';

export default class AddBalancePage extends Component {
    render() {
        return (
            <div className="addBalancePage">
                <Header />
                <PageTitle title="افزایش موجودی"/>

                <div className="main-content row">
                    <div className="col-xl-2 col-sm-12" />
                    <div className="col-xl-8 col-sm-12">
                        <AddBalanceForm/>
                    </div>
                    <div className="col-xl-2 col-sm-12" />
                </div>

                <div className="footer">
                    <Footer/>
                </div>
            </div>
        );
    }
}