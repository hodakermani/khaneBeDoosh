package khaneBeDoosh.domain;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nafise on 20/02/2018.
 */
public class User {

    private boolean isAdmin = false;

    protected String name;

    public User(String _name) {
        this.name = _name;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
