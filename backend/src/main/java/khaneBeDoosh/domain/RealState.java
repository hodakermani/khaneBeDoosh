package khaneBeDoosh.domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nafise on 20/02/2018.
 */
public class RealState extends User {

    private String url;

    public RealState(String _name, String _url) {
        super(_name);
        this.url = _url;
    }

    public String getUrl() { return this.url; }
}
