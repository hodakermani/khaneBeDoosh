package khaneBeDoosh.controller;

import khaneBeDoosh.domain.*;
import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class getHouseCtrl {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/secure/api/houses")
    public ViewedResult getHouse() throws SQLException, IOException, JSONException {
        Boolean success = true;
        String msg = "";
        return new ViewedResult(counter.incrementAndGet(), success, msg, App.getViewedHouse());
    }
}
