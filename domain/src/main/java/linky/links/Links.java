package linky.links;

import java.util.Optional;

public interface Links {
    void add(final NewLink newLink);

    Optional<Link> findBy(Link.Name linkName);
}