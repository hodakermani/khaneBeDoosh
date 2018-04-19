package khaneBeDoosh.controller;

import khaneBeDoosh.domain.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nafise on 06/04/2018.
 */

@RestController
public class HouseDetailCtrl {
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/api/houseDetails")
    public HouseDetail houseDetail(@RequestParam(value="id", defaultValue="") String id,
                               @RequestParam(value="parentName", defaultValue="خانه به دوش") String parentName) throws SQLException {
        Boolean success = false;
        String msg = "";
        House house = new House();

        try {
            house = App.getHouseDetails(id, parentName);
            if (house != null) {
                success = true;
                msg = "خانه با موفقیت یافت شد.";
            }
            else {
                msg = "خانه‌ای یافت نشد.";
            }
        } catch (JSONException e) {
            msg = "خانه‌ای یافت نشد.";
            e.printStackTrace();
        } catch (IOException e) {
            msg = "خانه‌ای یافت نشد.";
            e.printStackTrace();
        }

        return new HouseDetail(counter.incrementAndGet(), success, msg, house, "دریافت شماره مالک/مشاور");
    }

    @RequestMapping("/api/houseDetailsGetPhone")
    public HouseDetail getPhone(@RequestParam(value="id", defaultValue="") String id,
                                @RequestParam(value="parentName", defaultValue="http://acm.ut.ac.ir/khaneBeDoosh/v2/house") String parentName,
                                @RequestParam(value = "name", defaultValue = "بهنام همایون") String name) {

        Boolean success = false;
        String msg = "";
        Map<String, User> users = App.getUsers();
        User user = users.get(name);
        String btnMsg = "";
        House house = new House();
        try {
            if (user != null) {
                house = App.getHouseDetails(id, parentName);
                if (house != null) {
                    success = App.viewPhone(name, house.getId(), house.getParentName());
                    msg = "در حال بررسی اعتبار می باشیم.";
                    btnMsg = (success) ? "شما قادر به دیدن شماره مالک/مشاور هستید. " + house.getPhone() : "اعتبار شما برای دریافت شماره مالک/مشاور کافی نیست";
                }
                else {
                    msg = "خانه‌ای یافت نشد.";
                }


            }
            else
                msg = "کاربر مورد نظر در سیستم موجود نمی‌باشد.";

        } catch (JSONException e) {
            msg = "عملیات موفقیت‌آمیز نبود!";
            e.printStackTrace();
        } catch (IOException e) {
            msg = "عملیات موفقیت‌آمیز نبود!";
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new HouseDetail(counter.incrementAndGet(), success, msg, house, btnMsg);
    }
}
