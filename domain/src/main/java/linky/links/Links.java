package linky.links;

import java.util.Optional;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public interface Links {

    void add(final NewLink newLink);

    Optional<Link> findBy(Name linkName);

    Stream<Link> all();

}