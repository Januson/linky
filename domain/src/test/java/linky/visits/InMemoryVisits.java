package linky.visits;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import linky.links.LinkVisited;
import linky.links.Name;

class InMemoryVisits implements Visits {

    private final Map<Name, LinkVisited> visits = new HashMap<>();

    @Override
    public void add(final LinkVisited newVisit) {
        this.visits.put(newVisit.destination(), newVisit);
    }

    @Override
    public Stream<LinkVisited> allOf(final Name link) {
        return this.visits.values().stream();
    }
}
