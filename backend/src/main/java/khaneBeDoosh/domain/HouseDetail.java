package khaneBeDoosh.domain;

/**
 * Created by nafise on 06/04/2018.
 */
public class HouseDetail {

    private final long id;
    private Boolean success;
    private String msg;
    private House data;
    private String btnMsg;

    public HouseDetail(long id, Boolean sucess, String msg, House data) {
        this.id = id;
        this.success = sucess;
        this.msg = msg;
        this.data = data;
    }

    public HouseDetail(long id, Boolean sucess, String msg, House data, String btnMsg) {
        this.id = id;
        this.success = sucess;
        this.msg = msg;
        this.data = data;
        this.btnMsg = btnMsg;
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

    public House getData() { return data; }

    public String getBtnMsg() { return btnMsg; }
}
