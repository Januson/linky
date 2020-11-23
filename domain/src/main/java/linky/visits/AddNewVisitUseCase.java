package linky.visits;

import linky.links.IsNameUsed;
import linky.links.LinkNotFound;
import linky.links.LinkVisited;

import javax.transaction.Transactional;

@Transactional
class AddNewVisitUseCase implements AddNewVisit {
    private final Visits visits;
    private final IsNameUsed isNameUsed;

    public AddNewVisitUseCase(final Visits visits, final IsNameUsed isNameUsed) {
        this.visits = visits;
        this.isNameUsed = isNameUsed;
    }

    @Override
    public void add(final LinkVisited newVisit) {
        if (!this.isNameUsed.isInUse(newVisit.destination())) {
            throw new LinkNotFound(newVisit.destination());
        }
        this.visits.add(newVisit);
    }

}
