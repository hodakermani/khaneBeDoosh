import React from 'react';
import {Link} from 'react-router';
import './AddHouse.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';
import 'font-awesome/css/font-awesome.css';

const AddHouse = (props) => {
    return (
        <div className="addHouse">
            <Link to={"/addHouse"} className="add-house">
                <span>صاحب خانه هستید؟ خانه خود را ثبت کنید</span>
            </Link>
        </div>
    )
};

export default AddHouse;
