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
@WebServlet("/addHouse")
public class HouseCtrl extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String buildingType = req.getParameter("buildingType");
        String area = req.getParameter("area");
        String dealType = req.getParameter("dealType");
        String price = req.getParameter("price");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        String description = req.getParameter("description");

        int _area = 0;
        int _price = 0;

        if ((area != null && area != "" && area instanceof String) &&
                (price != null && price != "" && price instanceof String) && phone != null) {
            try {
                _area = Integer.parseInt(area);
                try {
                    _price = Integer.parseInt(price);
                    // required attribute
                    House newHouse = new House(_area, buildingType, address, dealType, phone, description, _price, "بهنام همایون");
                    String msg = App.addHouse("بهنام همایون", newHouse);
                    req.setAttribute("msg", msg);
                } catch (NumberFormatException ex) {
                    req.setAttribute("msg", "قیمت وارد شده معتبر نمی‌باشد.");
                }
            } catch (NumberFormatException ex) {
                req.setAttribute("msg", "متراژ وارد شده معتبر نمی‌باشد.");
            }
        }

        else {
            req.setAttribute("msg", "برای اضافه شدن خانه به سامانه لطفا اطلاعات (متراژ، قیمت و تلفن) را به صورت صحیح وارد کنید.");
        }

        req.getRequestDispatcher("khaneBeDoosh").forward(req, resp);
    }

}
