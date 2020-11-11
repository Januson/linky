package linky.visits;

public class VisitsConfiguration {

    public AddNewVisit addNewVisit(final Visits visits) {
        return new AddNewVisitUseCase(visits);
    }

}
