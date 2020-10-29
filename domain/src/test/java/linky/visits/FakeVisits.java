package linky.visits;

class FakeVisits implements Visits {
    private NewVisit lastVisit;

    public NewVisit lastVisit() {
        return this.lastVisit;
    }

    @Override
    public void add(NewVisit newVisit) {
        this.lastVisit = newVisit;
    }
}
