import React, { Component } from 'react';
import {Link} from 'react-router';
import './SearchForm.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';

class SearchForm extends Component {

    constructor(props) {
        super(props);
        this.state = {
            maxPrice: "maxPrice",
            minArea: 0,
            buildingType: "هیچی",
            dealType: "هیچی",
            maxPriceValidator: '',
            minAreaValidator: '',
        };
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        // validation for all form elements
        this.validator();

        console.log(this.state);
        this.setState({[event.target.name]: event.target.value});
    }

    numberValidator(param, validatorName) {
        let test = /^\d+$|$/.test(param);

        console.log('------ test');
        console.log(test + validatorName);

        if (param === "" || test === true) {
            this.setState({[validatorName]: ""});
            return true;
        }
        else {
            this.setState({[validatorName]: "ورودی نامعتبر است."});
            return false;
        }
    }

    validator() {
        const v1 = this.numberValidator(this.state.minArea, "minAreaValidator");
        const v2 = this.numberValidator(this.state.maxPrice, "maxPriceValidator");
        return v1 && v2;
    }

    render() {
        return (
            <div className="search-container">
                <div className="row">
                    <div className="col-xl-4 form-item">
                        <select value={this.state.buildingType} onChange={this.handleChange} name="buildingType" >
                            <option value="هیچی" disabled>نوع ملک</option>
                            <option value="آپارتمان">آپارتمان</option>
                            <option value="ویلایی">ویلایی</option>
                        </select>
                    </div>
                    <div className="col-xl-4 form-item">
                        <input className="form-control" name="maxPrice" placeholder="حداکثر قیمت" onChange={this.handleChange} />
                        <div className="form-error">{this.state.maxPriceValidator}</div>
                        <span className="input-label price-label">تومان</span>
                    </div>
                    <div className="col-xl-4 form-item">
                        <input className="form-control" name="minArea" placeholder="حداقل متراژ" onChange={this.handleChange} />
                        <div className="form-error">{this.state.minAreaValidator}</div>
                        <span className="input-label">متر مربع</span>
                    </div>
                </div>
                <div className="row">
                    <div className="deal-type col-xl-6">
                        <input type="radio" name="dealType" value="اجاره"
                               checked={this.state.dealType === "اجاره"}
                               onChange={this.handleChange} /><span>رهن و اجاره</span>
                        <span className="space" />
                        <input type="radio" name="dealType" value="خرید"
                               checked={this.state.dealType === "خرید"}
                               onChange={this.handleChange} /><span>خرید</span>
                    </div>
                    <div className="col-xl-6 col-sm-12">
                        <Link to={"/search/" + this.state.maxPrice + "/" + this.state.minArea + "/" + this.state.buildingType + "/" + this.state.dealType} className="search-btn shadowing">جستجو</Link>
                    </div>
                </div>
            </div>
        );
    }

}

export default SearchForm;
