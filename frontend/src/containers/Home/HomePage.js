import React, { Component } from 'react';
import Navbar from '../../components/Navbar/Navbar';
import Footer from '../../components/Footer/Footer';
import SlideShow from '../../components/SlideShow/SlideShow';
import SearchForm from '../../components/SearchForm/SearchForm';
import AddHouse from '../../components/AddHouse/AddHouse';
import WhyUs from '../../components/WhyUsContainer/WhyUsContainer';
import AdvantageContainer from "../../components/AdvantageContainer/AdvantageContainer";

import './HomePage.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';

export default class HomePage extends Component {
    render() {
        return (
            <div className="homePage">
                <SlideShow />
                <Navbar />
                <div className="main-content row">
                    <div className="col-xl-2 col-sm-12" />
                    <div className="col-xl-8 col-sm-12">
                        <SearchForm />
                        <AddHouse />
                        <AdvantageContainer />
                        <WhyUs />
                    </div>
                    <div className="col-xl-2 col-sm-12" />
                </div>
                <Footer />
            </div>
        );
    }
}