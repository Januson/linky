package linky.links;

public class Link {

    private final Name name;
    private final Url url;

    public Link(Name name, Url url) {
        this.name = name;
        this.url = url;
    }

    public Url url() {
        return this.url;
    }

    public Name name() {
        return this.name;
    }

}