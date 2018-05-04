package khaneBeDoosh.domain;

import java.util.List;

public class ViewedResult {
    private final long id;
    private boolean success;
    private String msg;
    private List<Viewed> viewed;

    public ViewedResult(long id, boolean success, String msg, List<Viewed> v) {
        this.id = id;
        this.success = success;
        this.msg = msg;
        this.viewed = v;
    }

    public long getId() {
        return id;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<Viewed> getViewed() {
        return viewed;
    }
}
