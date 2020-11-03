package linky.links;

public class NewLink {

    private final Link.Name name;
    private final Link.Url url;

    public NewLink(final Link.Name name, final Link.Url url) {
        this.name = name;
        this.url = url;
    }

    public Link.Name name() {
        return this.name;
    }

    public Link.Url url() {
        return this.url;
    }
}