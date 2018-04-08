import React, { Component } from 'react';

import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';
import PageTitle from '../../components/PageTitle/PageTitle';
import AddHouseForm from '../../components/AddHouseForm/AddHouseForm';

import './AddHousePage.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';


export default class AddHousePage extends Component {
    render() {
        return (
            <div className="addHousePage">

                <Header />
                <PageTitle title="ثبت ملک جدید در خانه به دوش"/>

                <div className="main-content row">
                    <div className="col-xl-2 col-sm-12" />
                    <div className="col-xl-8 col-sm-12">
                        <AddHouseForm/>
                    </div>
                    <div className="col-xl-2 col-sm-12" />
                </div>

                <Footer/>
            </div>
        );
    }
}