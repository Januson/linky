package linky.visits;

import linky.links.LinkVisited;

class FakeVisits implements Visits {
    private LinkVisited lastVisit;

    public LinkVisited lastVisit() {
        return this.lastVisit;
    }

    @Override
    public void add(LinkVisited newVisit) {
        this.lastVisit = newVisit;
    }
}
