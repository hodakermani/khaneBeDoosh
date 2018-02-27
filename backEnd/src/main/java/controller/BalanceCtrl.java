package controller;

import domain.*;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by nafise on 20/02/2018.
 */
@WebServlet("/addBalance")
public class BalanceCtrl extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String balance = req.getParameter("balance");
        Individual user = (Individual) App.getUser("بهنام همایون");

        if (balance != null && balance != "" && balance instanceof String) {
            try {
                int requestedBalance = Integer.parseInt(balance);
                Boolean success = Bank.addBalance(balance);
                if(success) {
                    user.addBalance((requestedBalance > 0) ? requestedBalance : 0);
                    req.setAttribute("msg", "عملیات افزایش اعتبار با موفقیت انجام شد.");
                } else {
                    req.setAttribute("msg", "برای افزایش اعتبار حساب لطفا چند ثانیه دیگر تلاش کنید.");
                }
            } catch (NumberFormatException ex) {
                req.setAttribute("msg", "اعتبار وارد شده معتبر نمی‌باشد.");
            } catch (JSONException e) {
                req.setAttribute("msg", "اطلاعات وارد شده معتبر نمی‌باشد.");
            }

        }
        req.getRequestDispatcher("khaneBeDoosh").forward(req, resp);

    }

}
