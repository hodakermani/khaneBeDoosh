package khaneBeDoosh.controller;

import khaneBeDoosh.domain.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nafise on 06/04/2018.
 */
@RestController
public class SearchCtrl {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/api/search")
    public SearchResult searchHouse(@RequestParam(value="buildingType", defaultValue="هیچی") String buildingType,
                                    @RequestParam(value="minArea", defaultValue="0") String minArea,
                                    @RequestParam(value="dealType", defaultValue="هیچی") String dealType,
                                    @RequestParam(value="maxPrice", defaultValue="2147483647") String maxPrice) throws SQLException {

        Boolean success = false;
        String msg = "";
        List<House> result = new ArrayList<House>();

        int _minArea;
        int _maxPrice = Integer.MAX_VALUE;

        try {
            _minArea = Integer.parseInt(minArea);
            if(!maxPrice.equals("maxPrice"))
                _maxPrice = Integer.parseInt(maxPrice);

            try {
                result = App.searchHouse(buildingType, _minArea, dealType, _maxPrice);
                if(result != null) {
                    success = true;
                    msg = "جست و جو با موفقیت انجام گرفت.";
                }
                else {
                    msg = "جست و جو نتیجه ای در بر نداشت.";
                }
            } catch (JSONException e) {
                msg = "جست و جو با موفقیت انجام نگرفت.";
                e.printStackTrace();
            } catch (IOException e) {
                msg = "جست و جو با موفقیت انجام نگرفت.";
                e.printStackTrace();
            }

        } catch (NumberFormatException e) {
            msg = "اطلاعات وارد شده معتبر نمی‌باشد.";
            e.printStackTrace();
        }

        return new SearchResult(counter.incrementAndGet(), success, msg, result);
    }

}
