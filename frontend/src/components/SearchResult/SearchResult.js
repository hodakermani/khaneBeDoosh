import React, {Component} from 'react';

import SearchResultItem from './SearchResultItem/SearchResultItem';

import './SearchResult.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';

class SearchResult extends Component {

    constructor(props) {
        super(props);
        this.state = {
            houses : [],
            result: 2,
        };
    }

    componentDidMount() {
        let url = 'http://172.30.48.139:4000/api/search';

        var obj = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                'minArea': this.props.minArea,
                'maxPrice': this.props.maxPrice,
                'buildingType': this.props.buildingType,
                'dealType': this.props.dealType,
            })
        };

        fetch(url, obj)
            .then((response) => response.json()).then((response) => {
            console.log(response);
            this.setState({
                houses: response.houses,
                result: (response.success) ? 1 : 0,
            });

            console.log(this.state.houses.length);
            console.log(this.props);
        });
    }

    render() {
        var col1 = [];
        var col2 = [];
        this.state.houses.map((house,index) => {
            if(index%2 === 0) {
                col1.push(<SearchResultItem key={index} house={house} />)
            }
            else {
                col2.push(<SearchResultItem key={index} house={house} />)
            }
        });

        return (
            <div className="searchResult">

                { (this.state.result === 1) ? (
                    <div className="search-info">
                        <span>برای مشاهده اطلاعات بیشتر درباره هر ملک روی آن کلیک کنید</span>
                    </div>
                ) :  (this.state.result === 0) ? (
                    <div className="search-info">
                        <span>اطلاعات درخواستی در سیستم موجود نمی‌باشد.</span>
                    </div>
                ) : (
                    <div className="search-info">
                        <span>سیستم در حال جستجو می‌باشد.</span>
                    </div>
                )}

                <div className="row">
                    <div className="col-xl-6 col-sm-12 item">
                        {col1}
                    </div>
                    <div className="col-xl-6 col-sm-12 item">
                        {col2}
                    </div>
                </div>

            </div>
        );
    }

}

export default SearchResult;
