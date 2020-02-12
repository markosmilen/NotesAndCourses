package mk.test.gamesbrowser.model;

public class Platform {
    private int id;
    private String abbreviation;
    private String name;
    private GameImage platform_logo;
    private String url;

    public Platform() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameImage getPlatform_logo() {
        return platform_logo;
    }

    public void setPlatform_logo(GameImage platform_logo) {
        this.platform_logo = platform_logo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
