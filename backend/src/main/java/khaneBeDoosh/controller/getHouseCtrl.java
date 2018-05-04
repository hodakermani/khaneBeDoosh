package khaneBeDoosh.controller;

import khaneBeDoosh.domain.*;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping("/secure/api/houses/all" )
//    @PreAuthorize("hasRole('ADMIN')")
    public ViewedResult getAllViewedHouses() throws JSONException, SQLException, IOException {
        return new ViewedResult(counter.incrementAndGet(), true, "admin", App.getViewedHouse(true, null));
    }

    @RequestMapping("/secure/api/houses")
//    @PreAuthorize("hasRole('USER')")
    public ViewedResult getHouse(@RequestParam(value="name", defaultValue="behnam") String name) throws SQLException, IOException, JSONException {
        return new ViewedResult(counter.incrementAndGet(), true, "individual",  App.getViewedHouse(false, name));
    }

}
