package linky.persistence.visits;


import linky.links.Ip;
import linky.visits.Origin;
import linky.visits.PendingOrigins;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class OriginPersistenceAdapter implements PendingOrigins {

    private final VisitsRepository visits;
    private final VisitMapper mapper;

    OriginPersistenceAdapter(final VisitsRepository visits,
                             final VisitMapper mapper) {
        this.visits = visits;
        this.mapper = mapper;
    }

    @Override
    public List<Origin.Pending> all() {
        return this.visits.findAllByGeoEncoding(Origin.GeoEncoding.PENDING)
            .map(this::toOrigin)
            .collect(Collectors.toList());
    }

    @Override
    public void update(final List<Origin.Encoded> origins) {
        for (Origin origin : origins) {
            this.visits.updateOrigin(
                origin.geoEncoding(),
                origin.country().toString(),
                origin.ip().toString()
            );
        }
        this.visits.flush();
    }

    private Origin.Pending toOrigin(final OriginView view) {
        return new Origin.Pending(new Ip(view.getOriginAddress()));
    }

}
