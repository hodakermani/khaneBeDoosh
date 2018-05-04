package khaneBeDoosh.domain;

public class HouseDetail {

    private final long id;
    private Boolean success;
    private String msg;
    private String username;
    private House data;
    private String btnMsg;

    public HouseDetail(long id, Boolean sucess, String msg, String username, House data, String btnMsg) {
        this.id = id;
        this.success = sucess;
        this.msg = msg;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public House getData() { return data; }

    public String getBtnMsg() { return btnMsg; }
}
