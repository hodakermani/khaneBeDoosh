package khaneBeDoosh.domain;

public class RealState extends User {

    private String url;

    public RealState(String _name, String _url) {
        super(_name);
        this.url = _url;
    }

    public String getUrl() { return this.url; }
}
