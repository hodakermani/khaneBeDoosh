package khaneBeDoosh.domain;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

import java.net.URL;
import java.net.HttpURLConnection;

public class JsonHandler {

    final static Logger logger = Logger.getLogger(App.class);

    public static JSONObject reader(String url) throws IOException, JSONException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject myResponse = new JSONObject(response.toString());
        return myResponse;
    }

    public static JSONObject getHouseDetails(String id, String url) throws IOException, JSONException {
        logger.info("URL = " + url);
        logger.info("ID = " + id);
        URL obj = new URL(url+"/"+id);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        if (con.getResponseCode() != 200)
            return null;
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject myResponse = new JSONObject(response.toString());
        return myResponse.getJSONObject("data");
    }

    public static Boolean addBalance(String _url, String apiKey, String userId, String value) throws IOException, JSONException {

        URL url = new URL(_url);
        JSONObject object = new JSONObject();
        object.put("userId", userId);
        object.put("value", value);

        byte[] postDataBytes = object.toString().getBytes("UTF-8");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setRequestProperty("apiKey", apiKey);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.getOutputStream().write(postDataBytes);
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0; )
                sb.append((char) c);
            String response = sb.toString();

            logger.debug(response);

            JSONObject myResponse = new JSONObject(response);
            boolean success = (myResponse.getString("result")).equals("OK");

            logger.info("----------------------- result for balance was :" + success);

            return success;
        }
        return false;
    }

}
