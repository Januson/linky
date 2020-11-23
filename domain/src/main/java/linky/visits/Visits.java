package linky.visits;

import linky.links.LinkVisited;
import linky.links.Name;

import java.util.stream.Stream;

public interface Visits {
    void add(LinkVisited newVisit);

    Stream<LinkVisited> allOf(Name link);
}