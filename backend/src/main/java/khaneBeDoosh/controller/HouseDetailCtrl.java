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

@RestController
public class HouseDetailCtrl {
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/api/houseDetails")
    public HouseDetail houseDetail(@RequestParam(value="id", defaultValue="") String id,
                                   @RequestParam(value="parentName", defaultValue="http://139.59.151.5:6664/khaneBeDoosh/v2/house") String parentName) throws SQLException {
        Boolean success = false;
        String msg = "";
        House house = null;
        User user = App.getUser();
        String username = (user != null)? user.getName() : null;

        id = Utility.convertToSafeString(id);
        parentName = Utility.convertToSafeString(parentName);

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

        return new HouseDetail(counter.incrementAndGet(), success, msg, username, house, "دریافت شماره مالک/مشاور");
    }

    @RequestMapping("/secure/api/houseDetailsGetPhone")
    public HouseDetail getPhone(@RequestParam(value="id", defaultValue="") String id,
                                @RequestParam(value="parentName", defaultValue="http://139.59.151.5:6664/khaneBeDoosh/v2/house") String parentName) {

        Boolean success = false;
        String msg = "";
        String btnMsg = "";
        House house = null;
        User user = App.getUser();
        String username = (user != null)? user.getName() : null;

        try {
            house = App.getHouseDetails(id, parentName);
            if (house != null) {
                success = App.viewPhone(user.getName(), house.getId(), house.getParentName());
                msg = "در حال بررسی اعتبار می باشیم.";
                btnMsg = (success) ? "شما قادر به دیدن شماره مالک/مشاور هستید. " + house.getPhone() : "اعتبار شما برای دریافت شماره مالک/مشاور کافی نیست";
            }
            else {
                msg = "خانه‌ای یافت نشد.";
            }
        } catch (JSONException e) {
            msg = "عملیات موفقیت‌آمیز نبود!";
            e.printStackTrace();
        } catch (IOException e) {
            msg = "عملیات موفقیت‌آمیز نبود!";
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new HouseDetail(counter.incrementAndGet(), success, msg, username, house, btnMsg);
    }
}
