package khaneBeDoosh.data;

import khaneBeDoosh.domain.Viewed;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by nafise on 16/04/2018.
 */
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
}
