package khaneBeDoosh.domain;

/**
 * Created by nafise on 06/04/2018.
 */
public class Balance {

    private final long id;
    private Boolean success;
    private String msg;
    private String name;
    private int currentBalance;

    public Balance(long id, Boolean sucess, String msg, String name, int currentBalance) {
        this.id = id;
        this.success = sucess;
        this.msg = msg;
        this.name = name;
        this.currentBalance = currentBalance;
    }

    public long getId() {
        return id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public String getName() {
        return name;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }
}
