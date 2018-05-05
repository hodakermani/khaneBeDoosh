package khaneBeDoosh.controller;

import khaneBeDoosh.domain.*;
import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class getHouseCtrl {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/secure/api/houses")
    public ViewedResult getHouse(@RequestParam(value="size", defaultValue="") String size,
                                 @RequestParam(value="page", defaultValue="") String page) throws SQLException, IOException, JSONException {
        Boolean success = true;
        String msg = "";
        int _size = Integer.parseInt(size);
        int _page = Integer.parseInt(page);

        return new ViewedResult(counter.incrementAndGet(), success, msg, App.getViewedHouse(_page, _size));
    }
}
