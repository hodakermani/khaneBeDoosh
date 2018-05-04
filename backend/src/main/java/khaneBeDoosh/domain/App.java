package khaneBeDoosh.domain;

import org.apache.log4j.Logger;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import khaneBeDoosh.data.*;

/**
 * Created by nafise on 20/02/2018.
 */
public class App {

    final static Logger logger = Logger.getLogger(App.class);

    private static User currUser;

    static {
        AppRepository.create();
        try {
            UserRepository.addIndividual("بهنام همایون", "bh1996", "behnam");
            currUser = UserRepository.findUser("behnam", "bh1996");
            UserRepository.addRealState("http://139.59.151.5:6664/khaneBeDoosh/v2/house", "خانه به دوش");
            HouseRepository.addRealStateHouses("http://139.59.151.5:6664/khaneBeDoosh/v2/house", "خانه به دوش");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User getUser() {
        return currUser;
    }

    public static String addHouse(House newHouse) throws SQLException {
        logger.info("-- App : Add House");
        HouseRepository.addHouse(newHouse);
        logger.info("New house added to system.");
        return "خانه با موفقیت به سیستم اضافه شد.";
    }

    public static List<House> searchHouse(String buildingType, int minArea, String dealType, int maxPrice)
            throws SQLException, IOException, JSONException {
        logger.info("-- App : Search House");
        int _dealType = Utility.stringToDealType(dealType);
        return HouseRepository.find(buildingType, minArea, _dealType, maxPrice);
    }

    public static House getHouseDetails(String id, String parentName) throws IOException, JSONException, SQLException {

        logger.info("-- App : View House Details");

        boolean isIndividual = UserRepository.isIndividual(parentName);

        House result = null;
        if (isIndividual == true) {
            result = HouseRepository.find(parentName, id);
        } else {
            String url = UserRepository.findRealState(parentName);
            if (url != null) {
                result = House.getDetails(id, url, parentName);
                HouseRepository.update(result);
            }
        }
        return result;
    }

    public static boolean viewPhone(String name, String houseId, String ownerName) throws SQLException {
        logger.info("-- App : View Phone");
        if (ViewedRepository.findByID(houseId, ownerName, name)) {
            return true;
        }
        int userBalance = UserRepository.getBalance(name);
        if (userBalance >= 1000) {
            // house-owner should not pay to see phone number
            if (!name.equals(ownerName)) {
                UserRepository.addBalance(-1000, name);
                ViewedRepository.addViewedHouse(houseId, ownerName, name);
            }

            return true;
        }

        return false;
    }

    public static List<Viewed> getViewedHouse(String name) throws SQLException, IOException, JSONException {
        List<Viewed> result = new ArrayList<Viewed>();
        User user = UserRepository.findUser(name, "bh1996");
        if (user.isAdmin())
            result = ViewedRepository.findAll();
        else
            result = ViewedRepository.findByName(name);
        return result;
    }

    public static User getLoginUser(String username, String password) throws SQLException {
        return UserRepository.findUser(username, password);
    }
}
