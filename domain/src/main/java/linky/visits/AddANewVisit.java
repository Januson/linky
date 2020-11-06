package linky.visits;

import linky.links.LinkVisited;

final class AddANewVisit implements AddNewVisit {
    private final Visits visits;

    public AddANewVisit(final Visits visits) {
        this.visits = visits;
    }

    @Override
    public void add(final LinkVisited newVisit) {
        this.visits.add(newVisit);
    }
}