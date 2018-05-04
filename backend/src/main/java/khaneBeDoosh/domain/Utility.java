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
}
