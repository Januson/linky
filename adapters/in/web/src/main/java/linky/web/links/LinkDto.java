package linky.web.links;

public class LinkDto {

    private final String name;
    private final String url;

    public LinkDto(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

}
