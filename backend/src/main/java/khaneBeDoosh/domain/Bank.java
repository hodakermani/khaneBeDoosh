package khaneBeDoosh.domain;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by nafise on 20/02/2018.
 */
public class Bank {

    private static String url = "http://acm.ut.ac.ir/ieBank/pay";
    private static String apiKey = "40ef2580-12e7-11e8-87b4-496f79ef1988";

    public static Boolean addBalance(String balance) throws IOException, JSONException {
        return JsonHandler.addBalance(url, apiKey, "123", balance);
    }

}
