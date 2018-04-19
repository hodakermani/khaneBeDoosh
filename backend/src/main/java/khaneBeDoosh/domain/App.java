package khaneBeDoosh.domain;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.apache.log4j.Logger;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import khaneBeDoosh.data.*;

/**
 * Created by nafise on 20/02/2018.
 */
public class App {

    final static Logger logger = Logger.getLogger(App.class);

    private static Map<String, User> users;
    private static User currUser;
    private static List<Viewed> views = new ArrayList<Viewed>();

    static {
        users = new HashMap<String, User>();
        users.put("بهنام همایون", new Individual("بهنام همایون", "behnam", "bh1996", 0));
        users.put("خانه به دوش", new RealState("خانه به دوش", "http://acm.ut.ac.ir/khaneBeDoosh/house"));
        AppRepository.create();
        try {
            UserRepository.addRealState("http://acm.ut.ac.ir/khaneBeDoosh/v2/house", "خانه به دوش");
            HouseRepository.addRealStateHouses("http://acm.ut.ac.ir/khaneBeDoosh/v2/house", "خانه به دوش");
            UserRepository.addIndividual("بهنام همایون", "bh1996", "behnam");
            currUser = UserRepository.findUser("behnam", "bh1996");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Map<String, User> getUsers() {
        return users;
    }

    public static User getUser(String name) {
        return users.get(name);
    }

    public static String addHouse(String name, House newHouse) throws SQLException {
        logger.info("-- App : Add House");
        Individual user = (Individual)App.getUser(name);

        if (user == null) {
            return "کاربر معتبری در سیستم موجود نمی‌باشد.";
        }

        HouseRepository.addHouse(newHouse);

        logger.info("New house added to system.");

        return  user.addHouse(newHouse);
    }

    // for real state user
    public static List<House> searchHouse(String buildingType, Integer minArea, String dealType, Integer maxPrice)
            throws IOException, JSONException, SQLException {
        logger.info("-- App : Search House");
        List<House> allHouses = HouseRepository.findAll();
        return filterResult(allHouses, buildingType, minArea, dealType, maxPrice);
    }

    private static List<House> filterResult(List<House> all, String buildingType, Integer _minArea, String dealType, Integer _maxPrice) {
        List<House> result = new ArrayList<House>();
        House.BuildingType bt  = Utility.stringToBuildingType(buildingType);

        logger.info("----------------------- SEARCHING FOR MATCHES -----------------------");

        int _dealType = Utility.stringToDealType(dealType);

        for (House house: all) {
            if (_minArea <= house.getArea() && (bt == house.getBuildingType() || bt == Utility.stringToBuildingType("هیچی"))) {

                if (_dealType == 0 && _dealType == house.getDealType()) {
                    if (_maxPrice >= house.getPrice().getSellPrice()) {
                        logger.info(result.size() + " : " + house.getId());
                        result.add(house);
                    }
                }

                else if (_dealType == 1 && _dealType == house.getDealType()) {
                    if (_maxPrice >= house.getPrice().getRentPrice()) {
                        logger.info(result.size() + " : " + house.getId());
                        result.add(house);
                    }
                }

                else if (_dealType == 2) {
                    if ((house.getDealType() == 0 && _maxPrice >= house.getPrice().getSellPrice()) ||
                            ( house.getDealType() == 1 && _maxPrice >= house.getPrice().getRentPrice())) {
                        logger.info(result.size() + " : " + house.getId());
                        result.add(house);
                    }
                }

            }
        }
        logger.info("----------------------- RESULTS FOUND -----------------------");
        return result;
    }

    public static House getHouseDetails(String id, String parentName) throws IOException, JSONException, SQLException {

        logger.info("-- App : View House Details");

        boolean isIndividual = UserRepository.isIndividual(parentName);

        House result = null;
        if (isIndividual == true) {
            result = HouseRepository.find(parentName, id);
        } else {
            result = House.getDetails(id, parentName, parentName);
            HouseRepository.update(result);
        }
        return result;






//        House result = null;
//        User u = App.getUser(parentName);
//        if (u == null) {
//            return result;
//        }
//        if (u instanceof Individual) {
//            result =  u.getHouse(id);
//        }
//        else {
//            String url = ((RealState) u).getUrl();
//            result = House.getDetails(id, url, parentName);
//        }
//        if (result != null)
//            House.setEmptyFields(result);
//        return result;
    }

    public static boolean viewPhone(String name, String houseId, String ownerName) throws SQLException {
        logger.info("-- App : View Phone");
        Individual user = (Individual) App.getUser(name);

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

}
