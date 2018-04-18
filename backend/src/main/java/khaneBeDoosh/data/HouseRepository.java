package khaneBeDoosh.data;

import khaneBeDoosh.domain.House;
import org.apache.log4j.Logger;

import java.sql.*;

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

    public static void addIndividualHouse(House newHouse) throws SQLException {
        logger.info("Add New Individual House");

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
        // TODO : setDate
        statement.setString(9, null);
        statement.setString(10, newHouse.getAddress());
        statement.setInt(11, newHouse.getPrice().getSellPrice());
        statement.setInt(12, newHouse.getPrice().getBasePrice());
        statement.setInt(13, newHouse.getPrice().getRentPrice());

        statement.executeUpdate();
        statement.close();

        con.close();
    }

}
