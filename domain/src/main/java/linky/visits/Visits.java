package linky.visits;

import java.util.stream.Stream;
import linky.links.LinkVisited;
import linky.links.Name;

public interface Visits {
    void add(LinkVisited newVisit);

    Stream<LinkVisited> allOf(Name link);
}
