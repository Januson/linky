package linky.persistence.visits;

import linky.links.Ip;
import linky.links.LinkVisited;
import linky.links.Name;
import linky.visits.Country;
import linky.visits.Origin;
import org.springframework.stereotype.Component;

@Component
class VisitMapper {

    VisitEntity toJpaEntity(final LinkVisited visit) {
        return new VisitEntity(
            visit.destination().toString(),
            visit.origin().ip().toString()
        );
    }

    LinkVisited toDomainEntity(final VisitEntity visit) {
        return new LinkVisited(
            new Name(visit.getDestination()), toOrigin(visit)
        );
    }

    private Origin toOrigin(final VisitEntity visit) {
        return switch (visit.getState()) {
            case DONE -> new Origin.Encoded(new Ip(visit.getIp()), new Country(visit.getCountry()));
            case PENDING -> new Origin.Pending(new Ip(visit.getIp()));
        };
    }

}