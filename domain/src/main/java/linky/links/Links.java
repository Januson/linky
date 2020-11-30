package linky.links;

import java.util.Optional;
import java.util.stream.Stream;

public interface Links {

    void add(final Link newLink);

    Optional<Link> findBy(Name linkName);

    Stream<Link> all();

}