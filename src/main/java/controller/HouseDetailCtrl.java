package controller;

import domain.*;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by nafise on 20/02/2018.
 */
@WebServlet("/houseDetails")
public class HouseDetailCtrl extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String parentName = req.getParameter("parentName");
        try {
            House house = App.getHouseDetails(id, parentName);
            req.setAttribute("house", house);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("houseDetails.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String parentName = req.getParameter("parentName");
        String name = req.getParameter("name");
        Map<String, User> users = App.getUsers();
        User user = users.get(name);

        try {
            if (user != null) {
                House house = App.getHouseDetails(id, parentName);
                req.setAttribute("house", house);
                String btnMsg = (App.viewPhone(name, house.getId(), house.getParentName())) ? "شماره مالک/مشاور: " + house.getPhone() : "اعتبار شما برای دریافت شماره‌ی مالک/مشاور کافی نیست!";
                req.setAttribute("type", "text");
                req.setAttribute("btn-msg", btnMsg);
                req.setAttribute("name", user.getName());
            }
            else
                req.setAttribute("msg", "کاربر مورد نظر در سیستم موجود نمی‌باشد.");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("houseDetails.jsp").forward(req, resp);
    }

}
