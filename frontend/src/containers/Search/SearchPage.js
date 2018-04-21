import React, { Component } from 'react';

import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';
import PageTitle from '../../components/PageTitle/PageTitle';
import SearchForm from '../../components/SearchForm/SearchForm';
import AddHouse from '../../components/AddHouse/AddHouse';
import SearchResult from '../../components/SearchResult/SearchResult';

import './SearchPage.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';

export default class SearchPage extends Component {
    constructor(props){
        super(props);
        this.fetch = this.fetch.bind(this);
        this.state = {
            houses:[]
        };
    }
    componentDidMount() {

    }
    shouldComponentUpdate(){
        console.log("UPDATE");
        this.fetch();
        return true;
    }
    fetch(){
        let url = '/api/search?minArea=' + this.props.params.minArea +
            '&buildingType=' + this.props.params.buildingType +
            '&dealType=' + this.props.params.dealType +
            '&maxPrice=' + this.props.params.maxPrice;
        fetch(url)
            .then((response) => response.json()).then((response) => {
            console.log(response);
            this.setState({
                houses: response.houses,
                result: (response.success) ? 1 : 0,
            });

            console.log("HOUSEs",this.state.houses);
            console.log(this.state.houses.length);
            console.log(this.props);
        });
        console.log(this.props.params);
    }

    render() {
        return (
            <div className="searchPage">
                <Header />
                <PageTitle title="نتایج جستجو"/>

                <div className="main-content row">
                    <div className="col-xl-2 col-sm-12" />
                    <div className="col-xl-8 col-sm-12">
                        <SearchResult
                            houses={this.state.houses}
                            minArea={this.props.params.minArea}
                            buildingType={this.props.params.buildingType}
                            dealType={this.props.params.dealType}
                            maxPrice={this.props.params.maxPrice} />
                        <div className="search-info">
                            <span>جستجوی مجدد</span>
                        </div>
                        <SearchForm inSearchResult="true" />
                        <AddHouse />
                    </div>
                    <div className="col-xl-2 col-sm-12" />
                </div>
                <Footer />
            </div>
        );
    }
}