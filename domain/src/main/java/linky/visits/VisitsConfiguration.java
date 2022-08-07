package linky.visits;

import linky.links.IsNameUsed;

public class VisitsConfiguration {

    public AddNewVisit addNewVisit(final Visits visits, final IsNameUsed isNameUsed) {
        return new AddNewVisitUseCase(visits, isNameUsed);
    }

    public EncodePendingVisits encodePendingVisits(final PendingOrigins pendingOrigins, final GeoEncoder geoEncoder) {
        return new EncodePendingVisitsUseCase(pendingOrigins, geoEncoder);
    }

    public FindAllVisits findAllVisits(final Visits visits, final IsNameUsed isNameUsed) {
        return new FindAllVisitsUseCase(isNameUsed, visits);
    }
}
