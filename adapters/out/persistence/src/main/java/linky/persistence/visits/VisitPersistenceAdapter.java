package linky.persistence.visits;


import linky.links.LinkVisited;
import linky.visits.Visits;
import org.springframework.stereotype.Component;

@Component
class VisitPersistenceAdapter implements Visits {

    private final VisitsRepository visits;
    private final VisitMapper mapper;

    VisitPersistenceAdapter(final VisitsRepository visits,
                            final VisitMapper mapper) {
        this.visits = visits;
        this.mapper = mapper;
    }

    @Override
    public void add(final LinkVisited newVisit) {
        this.visits.save(this.mapper.toJpaEntity(newVisit));
    }
}