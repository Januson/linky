package linky.links;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

class InMemoryLinks implements Links {

    private final Map<Name, Link> links = new HashMap<>();

    @Override
    public void add(final Link link) {
        this.links.put(link.name(), link);
    }

    @Override
    public Optional<Link> findBy(Name linkName) {
        return Optional.ofNullable(this.links.get(linkName));
    }

    @Override
    public Stream<Link> all() {
        return this.links.values().stream();
    }

}
