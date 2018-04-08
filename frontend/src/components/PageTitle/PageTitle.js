import React  from 'react';

import './PageTitle.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';

const PageTitle = (props) => {
    return (
        <div className="title-row">
            <span>{props.title}</span>
        </div>
    );
};

export default PageTitle;