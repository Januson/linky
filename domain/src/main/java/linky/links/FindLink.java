package linky.links;

import java.util.Optional;

public interface FindLink {
    Optional<Link> findBy(final Name linkName, Ip ip);
}
