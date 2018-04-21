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
            UserRepository.addRealState("http://acm.ut.ac.ir/khaneBeDoosh/v2/house", "خانه به دوش");
            HouseRepository.addRealStateHouses("http://acm.ut.ac.ir/khaneBeDoosh/v2/house", "خانه به دوش");
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
            throws SQLException {
        logger.info("-- App : Search House");
        int _dealType = Utility.stringToDealType(dealType);
        return HouseRepository.find(buildingType, minArea, _dealType, maxPrice);

//        return filterResult(allHouses, buildingType, minArea, dealType, maxPrice);
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

}
