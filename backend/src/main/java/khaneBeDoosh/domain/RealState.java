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

    public void addHouse(String id, String area, String buildingType, String address, int dealType, String imageURL,
                         String phone, String description, JSONObject price, String expireTime) throws JSONException {
        Price p = new Price();
        if (dealType == 0) {
            p = new Price(price.getInt("sellPrice"));
        }
        else {
            p = new Price(price.getInt("basePrice"), price.getInt("rentPrice"));
        }
        // TODO: change their format -> DATE  https://stackoverflow.com/questions/6510724/how-to-convert-java-string-to-date-object
        this.houses.add(new House(id, area, buildingType, address, dealType, imageURL, phone, description, p,
                new Date(), this.name));
    }

    @Override
    /**
     * Clear then read all houses from all real states and
     * add them to users
     */
    public List<House> getHouses() throws IOException, JSONException {
        this.clearHouses();
        this.readHouses();
        return this.houses;
    }

    public void readHouses() throws IOException, JSONException {
        JSONArray data = JsonHandler.reader(this.url);
        this.houseParser(data);
    }

    /**
     * add & parse
     * @param data
     * @throws JSONException
     */
    private void houseParser(JSONArray data) throws JSONException, IOException {
        List<House> newHouses = new ArrayList<House>();
        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonobject = data.getJSONObject(i);

            String id = jsonobject.getString("id");
            String area = jsonobject.getString("area");
            String buildingType = jsonobject.getString("buildingType");
            int dealType = jsonobject.getInt("dealType");
            String imageURL = jsonobject.getString("imageURL");
            JSONObject price = jsonobject.getJSONObject("price");
            String address = JsonHandler.getHouseDetails(id, this.url).getString("address");
            this.addHouse(id, area, buildingType, address, dealType, imageURL, null, null, price, null);
        }
    }

    private void clearHouses() {
        houses.clear();
    }

    public String getUrl() { return this.url; }

}
