package khaneBeDoosh.data;

import khaneBeDoosh.domain.App;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.UUID;

/**
 * Created by nafise on 14/04/2018.
 */
public class UserRepository {

    final static Logger logger = Logger.getLogger(App.class);

    public static void createTable(Connection c) throws SQLException {
        Statement stmt;
        stmt = c.createStatement();
        String sql = "CREATE TABLE Individual " +
                "(Username      VARCHAR(50)     PRIMARY KEY     NOT NULL," +
                " Password      VARCHAR(50)                     NOT NULL," +
                " Name          VARCHAR(50)                     NOT NULL," +
                " Phone         VARCHAR(50)                     NOT NULL," +
                " Balance       INT             DEFAULT 0)";
        stmt.executeUpdate(sql);

        sql = "CREATE TABLE RealState " +
                "(URL           VARCHAR(500)     PRIMARY KEY     NOT NULL," +
                " Name          VARCHAR(50)                      NOT NULL)";
        stmt.executeUpdate(sql);

        sql = "CREATE TABLE User " +
                "(ID             VARCHAR(50)    PRIMARY KEY    NOT NULL," +
                " IndividualID   VARCHAR(50)," +
                " RealStateID    VARCHAR(500)," +
                " IsIndividual   INT        NOT NULL," +
                " FOREIGN KEY (IndividualID) REFERENCES Individual(Username), " +
                " FOREIGN KEY (RealStateID) REFERENCES RealState(URL))";
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void addRealState(String URL, String name) throws SQLException {
        logger.info("Add New RealState");

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "INSERT INTO RealState (URL, Name) VALUES(?, ?);";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, URL);
        statement.setString(2, name);

        statement.executeUpdate();
        statement.close();

        sql = "INSERT INTO User (ID, IndividualID, RealStateID, IsIndividual) VALUES(?, ?, ?, ?);";
        statement = con.prepareStatement(sql);

        statement.setObject(1, UUID.randomUUID());
        statement.setString(2, null);
        statement.setString(3, URL);
        statement.setInt(4, 0);

        statement.executeUpdate();
        statement.close();

        con.close();
    }

    public static void addIndividual(String name, String password, String username) throws SQLException {
        logger.info("Add New Individual");

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "INSERT INTO Individual (Username, Password, Name, Phone, Balance) VALUES(?, ?, ?, ?, ?);";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, name);
        statement.setString(4, "");
        statement.setInt(5, 0);

        statement.executeUpdate();
        statement.close();

        sql = "INSERT INTO User (ID, IndividualID, RealStateID, IsIndividual) VALUES(?, ?, ?, ?);";
        statement = con.prepareStatement(sql);

        statement.setObject(1, UUID.randomUUID());
        statement.setString(2, name);
        statement.setString(3, null);
        statement.setInt(4, 1);

        statement.executeUpdate();
        statement.close();

        con.close();
    }

    public static int getBalance(String name) throws SQLException {
        logger.info("Get Balance of " + name);

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "SELECT * FROM Individual WHERE Name = ?;";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, name);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int b = resultSet.getInt("Balance");
            logger.info("User Found with Name " + name + " Balance " + b);
            con.close();
            return b;
        }

        logger.info("User " + name + " Not Found");
        con.close();
        return 0;
    }

    public static Boolean updateBalance(int balance, String name) throws SQLException {

        logger.info("Update Balance for " + name + " newbalance = " + balance);

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "UPDATE Individual SET Balance = ? WHERE Name = ?";

        PreparedStatement statement = con.prepareStatement(sql);

        statement.setInt(1, balance);
        statement.setString(2, name);
        statement.executeUpdate();

        con.close();

        return true;
    }

    public static Boolean addBalance(int balance, String name) throws SQLException {

        int currentBalance = getBalance(name);
        int newBalance = currentBalance + balance;

        logger.info("Update Balance for " + name + " and currbalance = " + currentBalance + " & newbalance = " + newBalance);

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "UPDATE Individual SET Balance = ? WHERE Name = ?";

        PreparedStatement statement = con.prepareStatement(sql);

        statement.setInt(1, newBalance);
        statement.setString(2, name);
        statement.executeUpdate();

        con.close();

        return true;
    }

}
