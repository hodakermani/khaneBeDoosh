package khaneBeDoosh.controller;

import khaneBeDoosh.domain.*;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nafise on 06/04/2018.
 */

@RestController
public class addHouseCtrl {
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/api/addHouse")
    public Message addHouse(@RequestParam(value="buildingType", defaultValue="") String buildingType,
                            @RequestParam(value="area", defaultValue="") String area,
                            @RequestParam(value="dealType", defaultValue="") String dealType,
                            @RequestParam(value="basePrice", defaultValue="") String basePrice,
                            @RequestParam(value="rentPrice", defaultValue="") String rentPrice,
                            @RequestParam(value="address", defaultValue="") String address,
                            @RequestParam(value="phone", defaultValue="") String phone,
                            @RequestParam(value="description", defaultValue="") String description) {

        Boolean success = false;
        String msg = "";
        House house = new House();

        int _area = 0;
        int _basePrice = 0;
        int _rentPrice = 0;

        if ((area != null && area != "" && area instanceof String) &&
                (basePrice != null && basePrice != "" && basePrice instanceof String) && phone != null &&
                (rentPrice instanceof String)) {
            try {
                _area = Integer.parseInt(area);
                try {
                    _basePrice = Integer.parseInt(basePrice);
                    if (rentPrice != null && rentPrice != "")
                        _rentPrice = Integer.parseInt(rentPrice);
                    // required attribute
                    House newHouse = new House(_area, buildingType, address, dealType, phone, description, _basePrice, _rentPrice, "بهنام همایون");
                    msg = App.addHouse("بهنام همایون", newHouse);
                    success = true;
                } catch (NumberFormatException ex) {
                    msg = "قیمت وارد شده معتبر نمی‌باشد.";
                }
            } catch (NumberFormatException ex) {
                msg = "متراژ وارد شده معتبر نمی‌باشد.";
            }
        } else {
            msg = "برای اضافه شدن خانه به سامانه لطفا اطلاعات (متراژ، قیمت و تلفن) را به صورت صحیح وارد کنید.";
        }


        return new Message(counter.incrementAndGet(), success, msg);
    }
}
