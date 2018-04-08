import React from 'react';

import './WhyUs.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';
import 'font-awesome/css/font-awesome.css';

const Advantage = (props) => {
    return (
        <div className="item">
            <i className="fa fa-check-circle"></i>
            <span>{props.content}</span>
        </div>
    )
};

export default Advantage;
