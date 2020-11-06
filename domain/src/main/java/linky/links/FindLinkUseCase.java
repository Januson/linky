package linky.links;

import linky.infrastructure.Events;

import java.util.Optional;

class FindLinkUseCase implements FindLink {

    private final Links links;
    private final Events<LinkVisited> events;

    FindLinkUseCase(final Links links, final Events<LinkVisited> events) {
        this.links = links;
        this.events = events;
    }

    @Override
    public Optional<Link> findBy(final Link.Name linkName) {
        final var link = this.links.findBy(linkName);
        link.ifPresent(l -> fireVisitedEvent(linkName));
        return link;
    }

    private void fireVisitedEvent(final Link.Name linkName) {
        this.events.fire(new LinkVisited(new Ip(), linkName));
    }
}
