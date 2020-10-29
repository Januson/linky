package linky.visits;

final class AddANewVisit {
    private final Visits visits;

    public AddANewVisit(final Visits visits) {
        this.visits = visits;
    }

    public void add(final NewVisit newVisit) {
        this.visits.add(newVisit);
    }
}