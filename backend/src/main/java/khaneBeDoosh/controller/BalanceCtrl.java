package khaneBeDoosh.controller;

import khaneBeDoosh.domain.*;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nafise on 05/04/2018.
 */

@RestController
public class BalanceCtrl {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/api/getBalance")
    public Balance getBalance(@RequestParam(value="name", defaultValue="بهنام همایون") String name) {
        Boolean success = true;
        String msg = "";
        Individual user = (Individual) App.getUser("بهنام همایون");
        return new Balance(counter.incrementAndGet(), success, msg, user.getBalance());
    }

    @RequestMapping("/api/addBalance")
    public Balance addBalance(@RequestParam(value="balance", defaultValue="") String balance) {
        Boolean success = false;
        String msg = "";
        Individual user = (Individual) App.getUser("بهنام همایون");

        if (balance != null && balance != "" && balance instanceof String) {
            try {
                int requestedBalance = Integer.parseInt(balance);
                if (requestedBalance > 0) {
                    success = Bank.addBalance(balance);
                    if (success) {
                        user.addBalance((requestedBalance > 0) ? requestedBalance : 0);
                        success = true;
                        msg = "عملیات افزایش اعتبار با موفقیت انجام شد.";
                    } else {
                        msg = "برای افزایش اعتبار حساب لطفا چند ثانیه دیگر تلاش کنید.";
                    }
                }
                else {
                    msg = "اعتبار وارد شده معتبر نمی‌باشد.";
                }
            } catch (NumberFormatException e) {
                msg = "اعتبار وارد شده معتبر نمی‌باشد.";
            } catch (JSONException e) {
                msg = "اطلاعات وارد شده معتبر نمی‌باشد.";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new Balance(counter.incrementAndGet(), success, msg, user.getBalance());
    }

}
