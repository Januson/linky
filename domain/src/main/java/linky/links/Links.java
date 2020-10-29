package linky.links;

import java.util.Optional;

interface Links {
    void add(final NewLink newLink);

    Optional<Link> findBy(Link.Name linkName);
}