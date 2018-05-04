package khaneBeDoosh.domain;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONException;

public class House {

    protected String id;
    protected Integer area;

    public enum BuildingType { ویلایی, آپارتمان, هیچی };
    BuildingType buildingType;

    protected String address;
    protected String imageURL;
    protected String parentName;

    protected int dealType; // if 0 -> sell, else -> rent

    protected String phone;
    protected String description;
    protected String expireTime;
    protected Price price;

    public House(String _id, int _area, String _buildingType, String _address, int _dealType,
                 String _imageURL, String _phone, String _description, Price _price, String expireTime, String _parentName) {
        this.id = _id;
        this.area = _area;
        this.buildingType = Utility.stringToBuildingType(_buildingType);
        this.address = _address;
        this.dealType = _dealType;
        this.phone = _phone;
        this.description = _description;
        this.price = _price;
        this.expireTime = (expireTime == null || expireTime == "") ? null : expireTime;
        this.imageURL = (_imageURL == null || _imageURL == "") ? "/images/no-pic.jpg" : _imageURL;
        this.parentName = _parentName;
    }

    public static House getDetails(String id, String url, String parentName) throws IOException, JSONException {

        JSONObject jsonobject = JsonHandler.getHouseDetails(id, url);
        if (jsonobject == null)
            return null;

        String idx = jsonobject.getString("id");
        int area = Integer.parseInt(jsonobject.getString("area"));
        String buildingType = jsonobject.getString("buildingType");
        String address = jsonobject.getString("address");
        int dealType = jsonobject.getInt("dealType");
        String imageURL = jsonobject.getString("imageURL");
        String phone = jsonobject.getString("phone");
        String description = jsonobject.getString("description");
        JSONObject price = jsonobject.getJSONObject("price");

        Price p = null;
        if (dealType == 0) {
            p = new Price(price.getInt("sellPrice"));
        }
        else {
            p = new Price(price.getInt("basePrice"), price.getInt("rentPrice"));
        }
        House house = new House(idx, area, buildingType, address, dealType, imageURL, phone, description, p, null, parentName);
        return house;
    }

    public String getId() {
        return id;
    }

    public Integer getArea() {
        return area;
    }

    public int getDealType() {
        return dealType;
    }

    public Price getPrice() {
        return price;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public String getParentName() { return parentName; }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

}
