package khaneBeDoosh.data;

import khaneBeDoosh.domain.*;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by nafise on 16/04/2018.
 */
public class HouseRepository {

    private static Logger logger = Logger.getLogger(AppRepository.class.getName());

    public static void createTable(Connection c) throws SQLException {
        Statement stmt;

        stmt = c.createStatement();
        String sql = "CREATE TABLE House " +
                "(ID            VARCHAR(50)     NOT NULL," +
                " ParentID      VARCHAR(500)    NOT NULL," +
                " Area          INT                             NOT NULL," +
                " BuildingType  VARCHAR(20)                     NOT NULL," +
                " ImageURL      VARCHAR(500)                    NOT NULL," +
                " Description   VARCHAR(500)," +
                " DealType      INT                             NOT NULL," +
                " Phone         VARCHAR(50)                     NOT NULL," +
                " ExpireTime    VARCHAR(50)," +
                " Address       VARCHAR(500)," +
                " SellPrice     INT," +
                " BasePrice     INT," +
                " RentPrice     INT, PRIMARY KEY (ID, ParentID))";
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void addHouse(House newHouse) throws SQLException {
//        logger.info("Add House");

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "INSERT INTO House (ID, ParentID, Area, BuildingType, ImageURL, Description, DealType, Phone," +
                " ExpireTime, Address, SellPrice, BasePrice, RentPrice) " + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, newHouse.getId());
        statement.setString(2, newHouse.getParentName());
        statement.setInt(3, newHouse.getArea());
        statement.setString(4, newHouse.getBuildingType().name());
        statement.setString(5, newHouse.getImageURL());
        statement.setString(6, newHouse.getDescription());
        statement.setInt(7, newHouse.getDealType());
        statement.setString(8, newHouse.getPhone());
        statement.setString(9, newHouse.getExpireTime());
        statement.setString(10, newHouse.getAddress());
        statement.setInt(11, newHouse.getPrice().getSellPrice());
        statement.setInt(12, newHouse.getPrice().getBasePrice());
        statement.setInt(13, newHouse.getPrice().getRentPrice());

        statement.executeUpdate();
        statement.close();

        con.close();
    }

    public static List<House> find(String buildingType, int minArea, int dealType, int maxPrice) throws SQLException, IOException, JSONException {
        logger.info("Get all houses");

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");


        // check expiration date of all houses and remove them if required
        int numOfModifiedRows = deleteHouses();

        // add houses again
        if (numOfModifiedRows > 0)
            addRealStateHouses("http://139.59.151.5:6664/khaneBeDoosh/v2/house", "خانه به دوش");

        // perform the filter
        String b1, b2;

        if (buildingType.equals("هیچی")) {
            b1 = "آپارتمان";
            b2 = "ویلایی";
        } else {
            b1 = buildingType;
            b2 = buildingType;
        }

        int d1, d2;
        if (dealType == 2) {
            d1 = 1;
            d2 = 0;
        } else {
            d1 = dealType;
            d2 = dealType;
        }

        String sql = "SELECT * from House WHERE Area >= ? AND SellPrice <= ? AND RentPrice <= ? AND (BuildingType = ? OR BuildingType = ?) AND (dealType = ? OR dealType = ?);";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setInt(1, minArea);
        statement.setInt(2, maxPrice);
        statement.setInt(3, maxPrice);
        statement.setString(4, b1);
        statement.setString(5, b2);
        statement.setInt(6, d1);
        statement.setInt(7, d2);

        ResultSet resultSet = statement.executeQuery();

        List<House> houses = new ArrayList<House>();

        while (resultSet.next()) {

            String _id = resultSet.getString("ID");
            String _parentID = resultSet.getString("ParentID");
            String _expireTime = resultSet.getString("ExpireTime");
            int _area = resultSet.getInt("Area");
            String _buildingType = resultSet.getString("BuildingType");
            String _imageUrl = resultSet.getString("ImageURL");
            String _description = resultSet.getString("Description");
            int _dealType = resultSet.getInt("DealType");
            String _phone = resultSet.getString("Phone");
            String _address = resultSet.getString("Address");
            int _sellPrice = resultSet.getInt("SellPrice");
            int _basePrice = resultSet.getInt("BasePrice");
            int _rentPrice = resultSet.getInt("RentPrice");

            Price _price;
            if (_dealType == 0)
                _price = new Price(_sellPrice);
            else
                _price = new Price(_basePrice, _rentPrice);

            House house = new House(_id, _area, _buildingType, _address, _dealType, _imageUrl, _phone, _description, _price, _expireTime, _parentID);
            houses.add(house);
        }

        statement.close();
        con.close();

        return houses;
    }

    private static int deleteHouses() throws SQLException {
        logger.info("*** Delete Houses ***");

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        // Get the current date
        Date currentDate = new Date();
        String timestamp = Long.toString(currentDate.getTime());
        logger.info("current time = " +timestamp);

        String sql = "DELETE FROM House WHERE ExpireTime < ? ;";

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, timestamp);

        int numOfModifiedRows = statement.executeUpdate();
        logger.info("*** number of affected rows: " + numOfModifiedRows);


        statement.close();

        con.close();
        return numOfModifiedRows;
    }

    public static void addRealStateHouses(String URL, String name) throws IOException, JSONException, SQLException {
        JSONObject response = JsonHandler.reader(URL);

        logger.debug("this is the real state houses");
        logger.debug(response);

        JSONArray data = response.getJSONArray("data");
        String expireTime = response.getString("expireTime");

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonobject = data.getJSONObject(i);

            String id = jsonobject.getString("id");
            int area = jsonobject.getInt("area");
            int dealType = jsonobject.getInt("dealType");
            JSONObject _price = jsonobject.getJSONObject("price");

            Price price;
            if (dealType == 0)
                price = new Price(_price.getInt("sellPrice"));
            else
                price = new Price(_price.getInt("basePrice"), _price.getInt("rentPrice"));

            String buildingType = jsonobject.getString("buildingType");
            String address = jsonobject.getString("address");
            String imageURL = jsonobject.getString("imageURL");

            // add house
            addHouse(new House(id, area, buildingType, address, dealType, imageURL, "", "", price, expireTime, name));
        }
    }

    public static House find(String parentName, String id) throws SQLException {
        logger.info("Find House with id of " + id + " parentName = " + parentName);

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "SELECT * FROM House WHERE ParentID = ? AND ID = ?;";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, parentName);
        statement.setString(2, id);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {

            int dealType = resultSet.getInt("DealType");
            Price _price;
            if (dealType == 0)
                _price = new Price(resultSet.getInt("SellPrice"));
            else
                _price = new Price(resultSet.getInt("BasePrice"), resultSet.getInt("RentPrice"));


            House house = new House(
                    resultSet.getString("ID"),
                    resultSet.getInt("Area"),
                    resultSet.getString("BuildingType"),
                    resultSet.getString("Address"),
                    dealType,
                    resultSet.getString("ImageURL"),
                    resultSet.getString("Phone"),
                    resultSet.getString("Description"),
                    _price,
                    resultSet.getString("ExpireTime"),
                    resultSet.getString("ParentID")
            );

            logger.info("House Found");
            con.close();
            return house;
        }

        logger.info("House Not Found");
        con.close();
        return null;
    }

    public static void update(House house) throws SQLException {
        logger.info("Update house with id " + house.getId());

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "UPDATE House SET Area = ?, BuildingType = ?, Address = ?, ImageURL = ?, Description = ?," +
                "DealType = ?, Phone = ?, SellPrice = ?, BasePrice = ?, RentPrice = ? WHERE ID = ? AND ParentID = ?";


        PreparedStatement statement = con.prepareStatement(sql);

        statement.setInt(1, house.getArea());
        statement.setString(2, house.getBuildingType().name());
        statement.setString(3, house.getAddress());
        statement.setString(4, house.getImageURL());
        statement.setString(5, house.getDescription());
        statement.setInt(6, house.getDealType());
        statement.setString(7, house.getPhone());
        statement.setInt(8, house.getPrice().getSellPrice());
        statement.setInt(9, house.getPrice().getBasePrice());
        statement.setInt(10, house.getPrice().getRentPrice());
        statement.setString(11, house.getId());
        statement.setString(12, house.getParentName());
        statement.executeUpdate();

        con.close();
    }
}
