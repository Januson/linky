package linky.persistence.visits;

import linky.links.LinkVisited;
import org.springframework.stereotype.Component;

@Component
class VisitMapper {

    VisitEntity toJpaEntity(final LinkVisited visit) {
        return new VisitEntity();
    }

}