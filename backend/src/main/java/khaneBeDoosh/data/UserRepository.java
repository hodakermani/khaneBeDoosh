package khaneBeDoosh.data;

import khaneBeDoosh.domain.Individual;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class UserRepository {

    final static Logger logger = Logger.getLogger(AppRepository.class.getName());

    public static void createTable(Connection c) throws SQLException {
        Statement stmt;
        stmt = c.createStatement();
        String sql = "CREATE TABLE Individual " +
                "(Username      VARCHAR(50)     PRIMARY KEY     NOT NULL," +
                " Password      VARCHAR(50)                     NOT NULL," +
                " Name          VARCHAR(50)                     NOT NULL," +
                " Phone         VARCHAR(50)                     NOT NULL," +
                " Balance       INT             DEFAULT 0," +
                " isAdmin       INT             DEFAULT 0 )";
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

    public static void addIndividual(String name, String password, String username, int isAdmin) throws SQLException {
        logger.info("Add New Individual");

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "INSERT INTO Individual (Username, Password, Name, Phone, Balance, IsAdmin) VALUES(?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, name);
        statement.setString(4, "");
        statement.setInt(5, 0);
        statement.setInt(6, isAdmin);

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

    public static Individual findUserByUsername(String username) throws SQLException {
        logger.info("find User with username " + username);

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "SELECT * FROM Individual WHERE Username = ?;";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, username);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Individual newUser = new Individual(resultSet.getString("Name"), resultSet.getString("Username"),
                                                resultSet.getString("Password"), resultSet.getInt("Balance"),
                                                resultSet.getInt("isAdmin"));
            con.close();
            return newUser;
        }

        logger.info("User with username " + username + " Not Found");
        con.close();
        return null;
    }

    public static List<Individual> findAll() throws SQLException {
        logger.info("find All User with username ");
        List<Individual> result = null;

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "SELECT * FROM Individual;";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String _name = resultSet.getString("Name");
            String _username = resultSet.getString("Username");
            String _password = resultSet.getString("Password");
            int _balance = resultSet.getInt("Balance");
            int _isAdmin = resultSet.getInt("isAdmin");
            Individual newUser = new Individual(_name, _username, _password, _balance, _isAdmin);
            result.add(newUser);
        }
        con.close();
        return result;
    }

    public static Individual findUser(String username, String password) throws SQLException {
        logger.info("find User with username " + username);

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "SELECT * FROM Individual WHERE Username = ? AND Password = ?;";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, username);
        statement.setString(2, password);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String _name = resultSet.getString("Name");
            String _username = resultSet.getString("Username");
            String _password = resultSet.getString("Password");
            int _balance = resultSet.getInt("Balance");
            int _isAdmin = resultSet.getInt("isAdmin");
            Individual newUser = new Individual(_name, _username, _password, _balance, _isAdmin);
            con.close();
            return newUser;
        }

        logger.info("User with username " + username + " Not Found");
        con.close();
        return null;
    }

    public static String findRealState(String name) throws SQLException {
        logger.info("find URL with username " + name);

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "SELECT * FROM RealState WHERE Name = ?;";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, name);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String URL = resultSet.getString("URL");
            con.close();
            return URL;
        }

        logger.info("RealState with username " + name + " Not Found");
        con.close();
        return null;
    }

    public static int getBalance(String name) throws SQLException {
        logger.info("Get Balance of " + name);

        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "SELECT Balance FROM Individual WHERE Name = ?;";
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

    public static boolean isIndividual(String parentId) throws SQLException {
        logger.info("is user with id " + parentId + " an  individual");
        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "SELECT * FROM Individual WHERE Name = ?;";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, parentId);
        boolean response = false;

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
           response = true;
        }

        con.close();
        return response;
    }

    public static String findUserByName(String name) throws SQLException {
        logger.info("find user with name " + name);
        Connection con = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");

        String sql = "SELECT * FROM Individual WHERE Name = ?;";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, name);

        ResultSet resultSet = statement.executeQuery();

        String username = null;

        while (resultSet.next()) {
            username = resultSet.getString("Username");
        }


        logger.info("babaaaaa ------- usernmae:" + username);

        con.close();
        return username;
    }
}
