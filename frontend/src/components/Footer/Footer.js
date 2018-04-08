import React from 'react';

import './Footer.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';
import 'font-awesome/css/font-awesome.css';

const Footer = (props) => {
    return (
        <footer className="row">
            <div className="col-xl-9 col-sm-12 text">
                <span>تمامی حقوق مادی و معنوی این وب‌سایت متعلق به نفیسه‌سادات سجادی و هدی کرمانی می‌باشد</span>
            </div>
            <div className="col-xl-3 col-sm-12 icons">
                <div>
                    <img src="/images/icons/twitter.png" alt="Twitter" />
                    <img src="/images/icons/telegram.png" alt="Telegram" />
                    <img src="/images/icons/instagram.png" alt="Instagram" />
                </div>
            </div>
        </footer>
    );
};

export default Footer;
