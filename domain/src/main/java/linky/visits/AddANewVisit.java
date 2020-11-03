package linky.visits;

final class AddANewVisit implements AddNewVisit {
    private final Visits visits;

    public AddANewVisit(final Visits visits) {
        this.visits = visits;
    }

    @Override
    public void add(final NewVisit newVisit) {
        this.visits.add(newVisit);
    }
}