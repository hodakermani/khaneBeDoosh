package domain;

/**
 * Created by nafise on 20/02/2018.
 */
public class Utility {

    public static int stringToInt(String s) {
        return Integer.parseInt(s);
    }

    public static House.BuildingType stringToBuildingType(String s) {
        if (s.equals("آپارتمان"))
            return House.BuildingType.آپارتمان;
        else if (s.equals("ویلایی"))
            return House.BuildingType.ویلایی;
        else
            return House.BuildingType.هیچی;

    }

    public static int stringToDealType(String s) {
        if (s.equals("خرید"))
            return  0;
        else if (s.equals("اجاره"))
            return 1;
        else
            return 2;
    }

    public static boolean isNotBlank(String s) {
        if (s != null && s != "")
            return true;
        return false;
    }

}
