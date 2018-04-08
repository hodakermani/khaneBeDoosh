package khaneBeDoosh.domain;

/**
 * Created by nafise on 20/02/2018.
 */
public class Viewed {

    private String viewerId;
    private String houseId;
    private String ownerId;


    public Viewed(String name, String houseId, String ownerId) {
        this.viewerId = name;
        this.houseId = houseId;
        this.ownerId = ownerId;
    }

    public String getHouseId() {
        return houseId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getViewerId() {
        return viewerId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setViewerId(String viewerId) {
        this.viewerId = viewerId;
    }

}
