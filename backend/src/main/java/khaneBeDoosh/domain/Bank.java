package khaneBeDoosh.domain;

import java.io.IOException;
import org.json.JSONException;

public class Bank {

    private static String url = "http://139.59.151.5:6664/bank/pay";
    private static String apiKey = "40ef2580-12e7-11e8-87b4-496f79ef1988";

    public static Boolean addBalance(String balance) throws IOException, JSONException {
        return JsonHandler.addBalance(url, apiKey, "123", balance);
    }

}
