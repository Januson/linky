package linky.links;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

class InMemoryLinks implements Links {

    private final Map<Link.Name, Link> links = new HashMap<>();

    @Override
    public void add(NewLink newLink) {
        final var link = new Link(
                newLink.name(),
                newLink.url()
        );
        this.links.put(link.name(), link);
    }

    @Override
    public Optional<Link> findBy(Link.Name linkName) {
        return Optional.ofNullable(this.links.get(linkName));
    }

    @Override
    public Stream<Link> all() {
        return this.links.values().stream();
    }

}
