package khaneBeDoosh.data;

import java.sql.*;

import org.apache.log4j.Logger;

/**
 * Created by nafise on 14/04/2018.
 */
public class AppRepository {

    private static Logger logger = Logger.getLogger(AppRepository.class.getName());

    public static void create() {

        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");

            c = DriverManager.getConnection("jdbc:sqlite:khaneBeDoosh.db");
            logger.info("Opened database successfully");

            UserRepository.createTable(c);
            HouseRepository.createTable(c);
            ViewedRepository.createTable(c);

            c.close();
        } catch ( Exception e ) {
            logger.error( e.getClass().getName() + ": " + e.getMessage() );
        }
        logger.info("Tables created successfully");

    }

}
