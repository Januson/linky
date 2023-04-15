package linky.visits;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.transaction.Transactional;
import linky.links.IsNameUsed;
import linky.links.LinkNotFound;
import linky.links.LinkVisited;
import linky.links.Name;

@Transactional
class FindAllVisitsUseCase implements FindAllVisits {
    private final IsNameUsed isNameUsed;
    private final Visits visits;

    public FindAllVisitsUseCase(final IsNameUsed isNameUsed, final Visits visits) {
        this.isNameUsed = isNameUsed;
        this.visits = visits;
    }

    @Override
    public List<LinkVisited> allOf(final Name name) {
        if (!this.isNameUsed.isInUse(name)) {
            throw new LinkNotFound(name);
        }
        return this.visits
                .allOf(name)
                .sorted(Comparator.comparing(LinkVisited::visitedAt))
                .collect(Collectors.toList());
    }
}
