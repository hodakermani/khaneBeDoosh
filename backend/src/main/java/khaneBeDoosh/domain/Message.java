package khaneBeDoosh.domain;

/**
 * Created by nafise on 06/04/2018.
 */
public class Message {

    private final long id;
    private final Boolean success;
    private final String msg;

    public Message(long id, Boolean success, String msg) {
        this.id = id;
        this.success = success;
        this.msg = msg;
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

}
