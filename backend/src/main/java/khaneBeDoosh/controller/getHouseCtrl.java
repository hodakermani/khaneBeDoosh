package khaneBeDoosh.controller;

import khaneBeDoosh.domain.*;
import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by nafise on 02/05/2018.
 */

@RestController
public class getHouseCtrl {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/api/houses")
    public ViewedResult getHouse(@RequestParam(value="name", defaultValue="behnam") String name) throws SQLException, IOException, JSONException {
        Boolean success = false;
        String msg = "";
        List<Viewed> result = App.getViewedHouse(name);
        return new ViewedResult(counter.incrementAndGet(), success, msg, result);
    }

}
