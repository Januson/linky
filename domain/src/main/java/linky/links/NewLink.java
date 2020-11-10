package linky.links;

import java.util.Optional;

public class NewLink {

    private final Name.Unvalidated name;
    private final Url.Unvalidated url;

    public NewLink(final Url.Unvalidated url) {
        this(null, url);
    }

    public NewLink(final Name.Unvalidated name, final Url.Unvalidated url) {
        this.name = name;
        this.url = url;
    }

    public Optional<Name.Unvalidated> name() {
        return Optional.ofNullable(this.name);
    }

    public Url.Unvalidated url() {
        return this.url;
    }

}
