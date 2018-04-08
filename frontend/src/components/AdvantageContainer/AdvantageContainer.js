import React from 'react';
import Advantage from './Advantage/Advantage';
import './AdvantageContainer.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';
import 'font-awesome/css/font-awesome.css';

const AdvantageContainer = (props) => {
    return (
        <div className="row advantageContainer">
            <Advantage
                title="آسان"
                description="به سادگی صاحب خانه شوید"
                imgSrc="/images/icons/726446.svg"
            />
            <Advantage
                title="مطمئن"
                description="با خیال راحت به دنبال خانه بگردید"
                imgSrc="/images/icons/726488.svg"
            />
            <Advantage
                title="گسترده"
                description="در منطقه مورد علاقه خود صاحب خانه شوید"
                imgSrc="/images/icons/726499.svg"
            />
        </div>
    );
};

export default AdvantageContainer;
