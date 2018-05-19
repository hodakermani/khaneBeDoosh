import React, { Component } from 'react';
import './AddHouseForm.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';
import {Redirect} from 'react-router-dom';

class AddHouseForm extends Component {

    constructor(props) {
        super(props);
        this.state = {
            buildingType: "نوع ملک",
            dealType: "اجاره",
            area: '',
            basePrice: '',
            rentPrice: '',
            address: '',
            phone: '',
            description: '',
            areaValidator: "",
            basePriceValidator: "",
            rentPriceValidator: "",
            addressValidator: "",
            phoneValidator: "",
            descriptionValidator: "",
            redirect: false,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleClick = this.handleClick.bind(this);
    }

    handleChange(event) {
        console.log(this.state);
        this.setState({[event.target.name]: event.target.value});
    }

    numberValidator(param, validatorName) {
        let test = /^\d+$/.test(param);

        console.log('------ test');
        console.log(test + validatorName);

        if (test === true || param === "") {
            this.setState({[validatorName]: ""});
            return true;
        }
        else {
            this.setState({[validatorName]: "ورودی نامعتبر است."});
            return false;
        }
    }

    phoneValidator(param, validatorName) {
        let test = /^\d+$/.test(param);

        console.log('------ test');
        console.log(test + validatorName);

        if ((param.length === 7 || param.length === 11) && test === true) {
            this.setState({[validatorName]: ""});
            return true;
        }
        else {
            this.setState({[validatorName]: "ورودی نامعتبر است."});
            return false;
        }
    }

    stringValidator(param, validatorName) {
        if (param.length > 255 && param && param !== "") {
            this.setState({[validatorName]: "ورودی نامعتبر است."});
            return false;
        }
        else {
            this.setState({[validatorName]: ""});
            return true;
        }
    }

    validator() {
        const v1 = this.numberValidator(this.state.area, "areaValidator");
        const v2 = this.numberValidator(this.state.basePrice, "basePriceValidator");
        const v3 = this.numberValidator(this.state.rentPrice, "rentPriceValidator");
        const v4 = this.phoneValidator(this.state.phone, "phoneValidator");
        const v5 = this.stringValidator(this.state.address, "addressValidator");
        const v6 = this.stringValidator(this.state.description, "descriptionValidator");
        return v1 && v2 && v3 && v4 && v5 && v6;
    }

    handleClick = () =>{
        // validation for all form elements
        const formValidation = this.validator();
        if (formValidation === false)
            return;

        let url = '';
        if(this.state.dealType === 'خرید') {
            url = 'http://172.20.10.4:4000/secure/api/addHouse?buildingType='+this.state.buildingType+'&area='+this.state.area+'&dealType='
                +this.state.dealType +'&basePrice='+this.state.basePrice+'&rentPrice=0&address='
                +this.state.address+'&phone='+this.state.phone+'&description='
                +this.state.description;
        }
        else {
            url = 'http://172.20.10.4:4000/secure/api/addHouse?buildingType='+this.state.buildingType+'&area='+this.state.area+'&dealType='
                +this.state.dealType +'&basePrice='+this.state.basePrice+'&rentPrice='+this.state.rentPrice+
                '&address='+this.state.address+'&phone='+this.state.phone+'&description='
                +this.state.description;
        }
        let header = 'Bearer ' + localStorage.getItem("loginToken");
        console.log("=============================");
        console.log(header);
        var obj = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': header,
            }
        };

        fetch(url, obj)
            .then(response => response.json()).then((response) => {

            console.log(response);

            console.log("this is user status: " + response.status);

            // user is unauthorized
            if (response.status === 401) {
                this.setState({ redirect: true });
            }

            else {
                this.setState({ redirect: false });
                if(response.success)
                    alert(response.msg);
            }
        });
    };

    render() {
        return (
            <div className="addHouseForm">

                {this.state.redirect && <Redirect to="/login" />}

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
                    <div className="col-xl-6" />
                </div>

                <div className="row">
                    <div className="col-xl-6 form-item">
                        <select value={this.state.buildingType} onChange={this.handleChange} name="buildingType" >
                            <option value="نوع ملک" disabled>نوع ملک</option>
                            <option value="آپارتمان">آپارتمان</option>
                            <option value="ویلایی">ویلایی</option>
                        </select>
                    </div>
                    <div className="col-xl-6 form-item">
                        <input className="form-control" name="area" placeholder="متراژ" onChange={this.handleChange} />
                        <div className="form-error">{this.state.areaValidator}</div>
                        <span className="input-label">متر مربع</span>
                    </div>
                </div>

                <div className="row">
                    <div className="col-xl-6 form-item">
                        <input className="form-control" name="address" placeholder="آدرس" onChange={this.handleChange} />
                        <div className="form-error">{this.state.addressValidator}</div>
                    </div>
                    <div className="col-xl-6 form-item">
                        <input className="form-control" name="basePrice"
                               placeholder={this.state.dealType === "هیچی" ?  "قیمت خرید/رهن" :
                                   this.state.dealType === "اجاره" ? "قیمت رهن" : "قیمت خرید"}
                               onChange={this.handleChange} />
                        <div className="form-error">{this.state.basePriceValidator}</div>
                        <span className="input-label">تومان</span>
                    </div>
                </div>

                <div className="row">
                    <div className="col-xl-6 form-item">
                        <input className="form-control" name="phone" placeholder="شماره تماس" onChange={this.handleChange} />
                        <div className="form-error">{this.state.phoneValidator}</div>
                    </div>
                    <div className="col-xl-6 form-item">
                        <input className="form-control" name="rentPrice"
                               placeholder={this.state.dealType === "هیچی" ?  "قیمت -/اجاره" :
                                   this.state.dealType === "اجاره" ? "قیمت اجاره" : ""}
                               onChange={this.handleChange} disabled={this.state.dealType !== "اجاره"} />
                        <div className="form-error">{this.state.rentPriceValidator}</div>
                        <span className="input-label">تومان</span>
                    </div>
                </div>
                <div className="extra-margin" />
                <div className="row">
                    <div className="col-xl-12 form-item">
                        <input className="form-control" name="description" placeholder="توضیحات"
                               onChange={this.handleChange} />
                        <div className="form-error">{this.state.descriptionValidator}</div>
                    </div>
                </div>
                <div className="row">
                    <div className="col-xl-6 col-sm-12" />
                    <div className="col-xl-6 col-sm-12">
                        <a onClick={this.handleClick} className="add-house-btn shadowing">ثبت ملک</a>
                    </div>
                </div>

            </div>
        );
    }
}

export default AddHouseForm;