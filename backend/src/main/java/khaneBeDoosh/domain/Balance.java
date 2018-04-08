package khaneBeDoosh.domain;

/**
 * Created by nafise on 06/04/2018.
 */
public class Balance {

    private final long id;
    private Boolean success;
    private String msg;
    private int currentBalance;

    public Balance(long id, Boolean sucess, String msg, int currentBalance) {
        this.id = id;
        this.success = sucess;
        this.msg = msg;
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

    public int getCurrentBalance() {
        return currentBalance;
    }
}
