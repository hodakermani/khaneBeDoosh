package khaneBeDoosh.domain;

import java.util.List;

public class SearchResult {

    private final long id;
    private boolean success;
    private String msg;
    private List<House> houses;

    public SearchResult(long id, boolean success, String msg, List<House> houses) {
        this.id = id;
        this.success = success;
        this.msg = msg;
        this.houses = houses;
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

    public List<House> getHouses() {
        return houses;
    }
}
