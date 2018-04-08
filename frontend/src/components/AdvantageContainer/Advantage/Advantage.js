import React from 'react';

import './Advantage.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';
import 'font-awesome/css/font-awesome.css';

const Advantage = (props) => {
    return (
        <div className="col-xl-4 col-sm-12 item">
            <div>
                <img src={props.imgSrc} alt={props.title} />
                <span className="title">{props.title}</span>
                <span className="description">{props.description}</span>
            </div>
        </div>
    )
};

export default Advantage;
