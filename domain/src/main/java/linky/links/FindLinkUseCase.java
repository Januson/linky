package linky.links;

import java.util.Optional;
import jakarta.transaction.Transactional;
import linky.infrastructure.Events;
import linky.visits.Origin;

@Transactional
class FindLinkUseCase implements FindLink {

    private final Links links;
    private final Events<LinkVisited> events;

    FindLinkUseCase(final Links links, final Events<LinkVisited> events) {
        this.links = links;
        this.events = events;
    }

    @Override
    public Optional<Link> findBy(final Name linkName, Ip origin) {
        final var link = this.links.findBy(linkName);
        link.ifPresent(l -> fireVisitedEvent(linkName, origin));
        return link;
    }

    private void fireVisitedEvent(final Name linkName, final Ip origin) {
        this.events.fire(new LinkVisited(linkName, new Origin.Pending(origin)));
    }
}
