package khaneBeDoosh.domain;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nafise on 20/02/2018.
 */
public class User {

    protected String name;
    protected List<House> houses;

    public User(String _name) {
        this.name = _name;
        houses = new ArrayList<House>();
    }

    public House getHouse(String id) {
        for(House house: houses) {
            if (house.getId().equals(id)) {
                return house;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public List<House> getHouses() throws IOException, JSONException { return houses; }

}
