package khaneBeDoosh.domain;

public class Utility {

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

    public static String convertToSafeString(String input) {
        return input.replace("<", "-")
                    .replace(">", "-")
                    .replace("&", "-")
                    .replace("'", "-")
                    .replace("\"", "-");
    }

    public static String addDashesToPhone(String phone) {
        return phone.substring(0, 3) + "-" + phone.substring(3, 6) + "-" + phone.substring(6);
    }
}
