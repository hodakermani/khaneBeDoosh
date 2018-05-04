package khaneBeDoosh.controller;

import khaneBeDoosh.domain.*;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.http.HttpStatus.*;

/**
 * Created by nafise on 02/05/2018.
 */

@RestController
public class getHouseCtrl {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/secure/api/houses")
    public ViewedResult getHouse(@RequestParam(value="name", defaultValue="behnam") String name) throws SQLException, IOException, JSONException {
        Boolean success = false;
        String msg = "";
        return new ViewedResult(counter.incrementAndGet(), success, msg, App.getViewedHouse(name));
    }

}
