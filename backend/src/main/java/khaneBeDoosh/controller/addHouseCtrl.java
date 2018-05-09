package khaneBeDoosh.controller;

import khaneBeDoosh.domain.*;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class addHouseCtrl {
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/secure/api/addHouse")
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

        buildingType = Utility.convertToSafeString(buildingType);
        address = Utility.convertToSafeString(address);
        phone = Utility.addDashesToPhone(Utility.convertToSafeString(phone));
        description = Utility.convertToSafeString(description);

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

                    Price _price;
                    int _dealType = 0;
                    if (dealType.equals("خرید"))
                        _price = new Price(_basePrice);
                    else {
                        _price = new Price(_basePrice, _rentPrice);
                        _dealType = 1;
                    }
                    User user = App.getUser();
                    House newHouse = new House(UUID.randomUUID().toString(), _area, buildingType, address,
                                                _dealType, null, phone, description, _price, "-1", user.getName());
                    msg = App.addHouse(newHouse);
                    success = true;
                } catch (NumberFormatException ex) {
                    msg = "قیمت وارد شده معتبر نمی‌باشد.";
                } catch (SQLException e) {
                    e.printStackTrace();
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
