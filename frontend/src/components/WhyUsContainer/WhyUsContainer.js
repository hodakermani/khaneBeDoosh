import React from 'react';

import './WhyUsContainer.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';
import 'font-awesome/css/font-awesome.css';

import WhyUs from './WhyUs/WhyUs';

const Advantage = (props) => {
    return (
        <div className="why">
            <div className="title">
                <span>چرا خانه به دوش؟</span>
            </div>
            <div className="row">
                <div className="col-xl-7 col-sm-7">
                    <div className="items">
                        <WhyUs content="اطلاعات کامل و صحیح از املاک قابل معامله" />
                        <WhyUs content="بدون محدودیت، ۲۴ ساعته و در تمام ایام هفته" />
                        <WhyUs content="جستجوی هوشمند ملک، صرفه‌جویی در زمان" />
                        <WhyUs content="تنوع در املاک، افزایش قدرت انتخاب مشتریان" />
                        <WhyUs content="بانکی جامع از اطلاعات هزاران آگهی به روز" />
                        <WhyUs content="دستیابی به نتیجه مطلوب در کمترین زمان ممکن" />
                        <WhyUs content="همکاری با مشاوران متخصص در حوزه املاک" />
                    </div>
                </div>
                <div className="col-xl-5 col-sm-5">
                    <img src="/images/why-khanebedoosh.jpg" alt="خانه به دوش" />
                </div>
            </div>
        </div>
    )
};

export default Advantage;
