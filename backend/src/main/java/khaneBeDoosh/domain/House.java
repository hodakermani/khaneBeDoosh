package khaneBeDoosh.domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by nafise on 20/02/2018.
 */
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
    protected Date expireTime;
    protected Price price;

    public House() {
        // TODO
    }

    // for real state
    public House(String _id, String _area, String _buildingType, String _address, int _dealType,
                 String _imageURL, String _phone, String _description, Price _price, Date expireTime, String _parentName) {
        this.id = _id;
        this.area = Utility.stringToInt(_area);
        this.buildingType = Utility.stringToBuildingType(_buildingType);
        this.address = _address;
        this.dealType = _dealType;
        this.phone = _phone;
        this.description = _description;
        this.price = _price;
        this.expireTime = expireTime;
        this.imageURL = (_imageURL == null || _imageURL == "") ? "no-pic.jpg" : _imageURL;
        this.parentName = _parentName;
    }

    // for individual
    public House(Integer _area, String _buildingType, String _address, String _dealType, String _phone,
                 String _description, Integer _basePrice, Integer _rentPrice, String _parentName) {
        this.id = UUID.randomUUID().toString();
        this.area = _area;
        this.buildingType = Utility.stringToBuildingType(_buildingType);
        this.address = _address;
        this.dealType = Utility.stringToDealType(_dealType);

        if (this.dealType == 0) {
            this.price = new Price(_basePrice);
        } else if (this.dealType == 1){
            this.price = new Price(_basePrice, _rentPrice);
        }

        this.phone = _phone;
        this.description = _description;
        this.parentName = _parentName;
        this.imageURL = "/images/no-pic.jpg";
    }

    public static House getDetails(String id, String url, String parentName) throws IOException, JSONException {

        JSONObject jsonobject = JsonHandler.getHouseDetails(id, url);
        if (jsonobject == null)
            return null;

        String idx = jsonobject.getString("id");
        String area = jsonobject.getString("area");
        String buildingType = jsonobject.getString("buildingType");
        String address = jsonobject.getString("address");
        int dealType = jsonobject.getInt("dealType");
        String imageURL = jsonobject.getString("imageURL");
        String phone = jsonobject.getString("phone");
        String description = jsonobject.getString("description");
        JSONObject price = jsonobject.getJSONObject("price");

        Price p = new Price();
        if (dealType == 0) {
            p = new Price(price.getInt("sellPrice"));
        }
        else {
            p = new Price(price.getInt("basePrice"), price.getInt("rentPrice"));
        }
        House house = new House(idx, area, buildingType, address, dealType, imageURL, phone, description, p, null, parentName);
        return house;
    }

    public static void setEmptyFields(House house) {
        // TODO
//        house.area = (Utility.isNotBlank(house.area)) ? house.area : "ندارد!";
        house.address = (Utility.isNotBlank(house.address)) ? house.address : "ندارد!";
        house.description = (Utility.isNotBlank(house.description)) ? house.description : "ندارد!";
        house.phone = (Utility.isNotBlank(house.phone)) ? house.phone : "ندارد!";

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

    public Date getExpireTime() {
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

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

}
