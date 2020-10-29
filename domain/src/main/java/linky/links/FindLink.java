package linky.links;

import java.util.Optional;

interface FindLink {
    Optional<Link> findBy(final Link.Name linkName);
}
