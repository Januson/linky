package linky.links;

import java.util.Optional;

class InMemoryLinks implements Links {

    @Override
    public void add(NewLink newLink) {

    }

    @Override
    public Optional<Link> findBy(Link.Name linkName) {
        return Optional.empty();
    }
}
