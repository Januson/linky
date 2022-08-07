package linky.visits;

import java.util.stream.Stream;
import linky.links.LinkVisited;
import linky.links.Name;

class FakeVisits implements Visits {
    private LinkVisited lastVisit;

    public LinkVisited lastVisit() {
        return this.lastVisit;
    }

    @Override
    public void add(LinkVisited newVisit) {
        this.lastVisit = newVisit;
    }

    @Override
    public Stream<LinkVisited> allOf(Name link) {
        return Stream.empty();
    }
}
