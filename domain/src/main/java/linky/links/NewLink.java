package linky.links;

public class NewLink {

    private final Link.Name name;

    public NewLink(Link.Name name) {
        this.name = name;
    }

    public Link.Name name() {
        return this.name;
    }

}