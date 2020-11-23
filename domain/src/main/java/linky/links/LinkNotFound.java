package linky.links;

public class LinkNotFound extends RuntimeException {
    public LinkNotFound(final Name linkName) {
        super(String.format("Link with name: '%s' was not found!", linkName));
    }
}