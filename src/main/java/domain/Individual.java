package domain;

/**
 * Created by nafise on 20/02/2018.
 */
public class Individual extends User {

    private String phone;
    private int balance;
    private String username;
    private String password;

    public Individual(String _name, String _username, String _password) {
        super(_name);
        this.balance = 0;
        this.username = _username;
        this.password = _password;
    }

    public String getPhone() {
        return phone;
    }

    public int getBalance() {
        return balance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addBalance(int _balance) {
        this.balance += _balance;
    }

    public void setBalance(int balance) { this.balance = balance; }

    public String addHouse(House newHouse) {

        if (newHouse.getArea() < 0 || newHouse.getPrice().getSellPrice() < 0 || newHouse.getPrice().getRentPrice() < 0) {
            return "اطلاعات وارد شده معتبر نمی‌باشد، لطفا مجددا تلاش کنید.";
        }
        houses.add(newHouse);
        return "خانه با موفقیت به سیستم اضافه شد.";
    }

}
