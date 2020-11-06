package linky.links;

import java.util.Optional;
import java.util.stream.Stream;

class InMemoryLinks implements Links {

    @Override
    public void add(NewLink newLink) {

    }

    @Override
    public Optional<Link> findBy(Link.Name linkName) {
        return Optional.empty();
    }

    @Override
    public Stream<Link> all() {
        return null;
    }
}
