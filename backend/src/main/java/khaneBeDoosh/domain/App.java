package khaneBeDoosh.domain;

import khaneBeDoosh.data.*;

import org.json.JSONException;
import org.apache.log4j.Logger;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;

public class App {

    final static Logger logger = Logger.getLogger(App.class);

    private static User currUser = null;

    static {
        AppRepository.create();
        try {
            UserRepository.addIndividual("بهنام همایون", "bh1996", "behnam", 0);
            UserRepository.addIndividual("هدی کرمانی", "hoda1375", "hoda", 0);
            UserRepository.addIndividual("ادمین", "admin", "admin", 1);
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

    public static void setUser(User user) { currUser = user; }

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
            if (!name.equals(ownerName)) {
                UserRepository.addBalance(-1000, name);
                ViewedRepository.addViewedHouse(houseId, ownerName, name);
            }
            return true;
        }
        return false;
    }

    public static List<Viewed> getViewedHouse() throws SQLException, IOException, JSONException {
        logger.info("-- App: return viewed house");
        List<Viewed> result;
        Individual user = (Individual) currUser;
        if (user.getIsAdmin() == 1) {
            logger.info("Admin :D");
            result = ViewedRepository.findAll();
        }
        else
            result = ViewedRepository.findByName(currUser.getName());
        return result;
    }

    public static void setLoginUser(Individual user) throws SQLException {
        logger.info("-- App : Set Login User");
        currUser = user;
    }

    public static User getRequestedUser(String username, String password) throws SQLException {
        return UserRepository.findUser(username, password);
    }
}
