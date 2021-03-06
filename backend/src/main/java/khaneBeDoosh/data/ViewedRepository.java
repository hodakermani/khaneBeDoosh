package khaneBeDoosh.data;

import khaneBeDoosh.domain.Viewed;

import org.json.JSONException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class ViewedRepository {

    private static Logger logger = Logger.getLogger(AppRepository.class.getName());

    public static void createTable(Connection c) throws SQLException {
        Statement stmt;

        stmt = c.createStatement();
        String sql = "CREATE TABLE Viewed " +
                "(HouseID       VARCHAR(50)    NOT NULL," +
                " ParentID      VARCHAR(500)   NOT NULL," +
                " IndividualID  VARCHAR(50)    NOT NULL," +
                " FOREIGN KEY (HouseID) REFERENCES House(ID), " +
                " FOREIGN KEY (ParentID) REFERENCES House(ParentID), " +
                " FOREIGN KEY (IndividualID) REFERENCES Individual(Username), PRIMARY KEY (HouseID, ParentID, IndividualID))";
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void addViewedHouse(String HouseID, String parentID, String IndividualID) throws SQLException {
        logger.info("Add New Viewed House");

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "INSERT INTO Viewed (HouseID, ParentID, IndividualID) " + "VALUES(?, ?, ?);";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, HouseID);
        statement.setString(2, parentID);
        statement.setString(3, IndividualID);

        statement.executeUpdate();
        statement.close();

        con.close();
    }

    public static Boolean findByID(String HouseID, String parentID, String IndividualID) throws SQLException {
        logger.info("Search for House " + HouseID);

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "SELECT * FROM Viewed WHERE HouseID = ?;";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, HouseID);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String pID = resultSet.getString("ParentID");
            logger.info("Viewed Found with Name " + pID + " ID " + HouseID);
            if (parentID.equals(pID)) {
                con.close();
                return true;
            }
        }

        logger.info("Viewed " + HouseID + " Not Found");
        con.close();
        return false;
    }

    public static List<Viewed> findAll(int page, int size) throws SQLException, IOException, JSONException {
        logger.info("Get all viewed houses");

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "SELECT * from Viewed;";
        PreparedStatement statement = con.prepareStatement(sql);

        ResultSet resultSet = statement.executeQuery();

        int startIndex = (page - 1) * size ;
        int endIndex = startIndex + size -1;
        int i = 0;
        List<Viewed> v = new ArrayList<Viewed>();

        logger.info("this is the table size:" + startIndex);
        logger.info("this is the table size:" + endIndex);

        while (resultSet.next()) {
            if (i < startIndex) {
                i++;
                continue;
            }
            else if (i > endIndex)
                break;
            String _id = resultSet.getString("HouseID");
            String _parentID = resultSet.getString("ParentID");
            String _individualID = resultSet.getString("IndividualID");
            Viewed viewed = new Viewed(_individualID, _id, _parentID);
            v.add(viewed);
            i++;
        }

        statement.close();
        con.close();
        return v;
    }

    public static List<Viewed> findByName(String name, int page, int size) throws SQLException {
        logger.info("Get all viewed houses");

//        String username = UserRepository.findUserByName(name);

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "SELECT * from Viewed WHERE IndividualID = ?;";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, name);

        logger.info("this is username" + name);

        ResultSet resultSet = statement.executeQuery();

        int startIndex = (page - 1) * size ;
        int endIndex = startIndex + size -1;
        int i = 0;
        List<Viewed> v = new ArrayList<Viewed>();

        logger.info("this is the table size:" + startIndex);
        logger.info("this is the table size:" + endIndex);

        while (resultSet.next()) {
            if (i < startIndex) {
                i++;
                continue;
            }
            else if (i > endIndex)
                break;
            Viewed viewed = new Viewed(resultSet.getString("IndividualID"), resultSet.getString("HouseID"), resultSet.getString("ParentID"));
            v.add(viewed);
            i++;
        }



//        while (resultSet.next()) {
//            Viewed viewed = new Viewed(resultSet.getString("IndividualID"), resultSet.getString("HouseID"), resultSet.getString("ParentID"));
//            v.add(viewed);
//        }

        statement.close();
        con.close();
        return v;
    }
}
