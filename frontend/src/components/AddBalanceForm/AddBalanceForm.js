import React, { Component } from 'react';
import './AddBalanceForm.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';

class AddBalanceForm extends Component {

    constructor(props) {
        super(props);
        this.state = {
            balance: '',
            requsetedBalance: '',
            formErrors: '',
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleClick = this.handleClick.bind(this);
        this.translateNum =  this.translateNum.bind(this);
    }

    handleChange(event) {
        console.log(this.state);
        this.setState({[event.target.name]: event.target.value});
    }

    translateNum(m) {
        var num=JSON.parse('{"0":"۰","1":"۱","2":"۲","3":"۳","4":"۴","5":"۵","6":"۶","7":"۷","8":"۸","9":"۹"}');
        return m.replace(/./g,function(c){
            return (typeof num[c]==="undefined")?
                ((/\d+/.test(c))?c:''):
                num[c];
        })
    }

    componentDidMount() {
        let url = '/api/addBalance';
        fetch(url)
            .then(response => response.json()).then((response) => {
            console.log(response);

            this.setState({
                balance: response.currentBalance,
            });
        });
    }

    validator() {
        const value = Number(this.state.requsetedBalance);
        if (value >= 0) {
            this.setState({formErrors: ""});
            return true;
        }
        else {
            this.setState({formErrors: "ورودی نامعتبر است."});
            return false;
        }
    }

    handleClick = () =>{

        const validation = this.validator();
        if (validation === false)
            return;

        let url = '/api/addBalance?balance=' + this.state.requsetedBalance;
        fetch(url)
            .then(response => response.json()).then((response) => {
            console.log(response);

            if (response.success === true) {
                alert(response.msg);

                // send to the parent that this was successful
                this.props.updateBalance();
            }

            this.setState({
                balance: response.currentBalance,
            })
        });
    };

    render() {
        return (
            <div className="addBalanceForm">
                <div className="row">
                    <div className="col-xl-6">
                        <div className="row">
                            <div className="col-xl-6 col-sm-6 balance">
                                <span>اعتبار کنونی</span>
                            </div>
                            <div className="col-xl-6 col-sm-6 balance">
                                <span>{this.translateNum(String(this.state.balance))}</span>
                                <span className="text-gray toman">تومان</span>
                            </div>
                        </div>
                    </div>
                    <div className="col-xl-6 col-sm-12 form-item">
                        <input className="form-control" name="requsetedBalance" placeholder="مبلغ مورد نظر" onChange={this.handleChange} />
                        <span className="price-label">تومان</span>
                    </div>
                </div>
                <div className="form-error">
                    {this.state.formErrors}
                </div>
                <div className="row">
                    <div className="col-xl-6 col-sm-12" />
                    <div className="col-xl-6 col-sm-12">
                        <a onClick={this.handleClick} className="add-house-btn shadowing">افزایش اعتبار</a>
                    </div>
                </div>
            </div>
        );
    }
}

export default AddBalanceForm;