package linky.links;

public class NewLink {

    private final Name name;
    private final Url url;

    public NewLink(final Name name, final Url url) {
        this.name = name;
        this.url = url;
    }

    public Name name() {
        return this.name;
    }

    public Url url() {
        return this.url;
    }

}
