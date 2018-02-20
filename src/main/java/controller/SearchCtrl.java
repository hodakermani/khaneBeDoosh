package controller;

import domain.*;
import org.json.JSONException;

import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by nafise on 20/02/2018.
 */

@WebServlet("/search")
public class SearchCtrl extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String buildingType = req.getParameter("buildingType");
        String minArea = req.getParameter("area");
        String dealType = req.getParameter("dealType");
        String maxPrice = req.getParameter("price");
        int _minArea = 0;
        int _maxPrice = Integer.MAX_VALUE;
        try {
            if (minArea != null && minArea != "")
                _minArea = Integer.parseInt(minArea);
            if (maxPrice != null && maxPrice != "")
                _maxPrice = Integer.parseInt(maxPrice);

            try {
                List<House> result = App.searchHouse(buildingType, _minArea, dealType, _maxPrice);
                req.setAttribute("houses", result);
                req.getRequestDispatcher("search.jsp").forward(req, resp);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (NumberFormatException ex) {
            req.setAttribute("msg", "اطلاعات وارد شده معتبر نمی‌باشد.");
            req.getRequestDispatcher("khaneBeDoosh").forward(req, resp);
        }
    }

}
