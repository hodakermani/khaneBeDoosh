package khaneBeDoosh.controller;

import khaneBeDoosh.data.UserRepository;
import khaneBeDoosh.domain.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceCtrl {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/secure/api/getBalance")
    public Balance getBalance() throws SQLException {
        Boolean success = true;
        String msg = "";
        User user = App.getUser();
        return new Balance(counter.incrementAndGet(), success, msg, user.getName(), UserRepository.getBalance(user.getName()));
    }

    @RequestMapping("/secure/api/addBalance")
    public Balance addBalance(@RequestParam(value="balance", defaultValue="") String balance) throws SQLException {
        Boolean success = false;
        String msg = "";
        User user = App.getUser();
        if (balance != null && balance != "" && balance instanceof String) {
            try {
                int requestedBalance = Integer.parseInt(balance);
                if (requestedBalance > 0) {
                    success = Bank.addBalance(balance);
                    if (success) {
                        UserRepository.addBalance((requestedBalance > 0) ? requestedBalance : 0, user.getName());
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new Balance(counter.incrementAndGet(), success, msg, user.getName(), UserRepository.getBalance(user.getName()));
    }

}
