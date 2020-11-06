package linky.links;

import linky.infrastructure.Events;

public class LinksConfiguration {

    public CreateNewLink createNewLink(final Links links) {
        return new CreateNewLinkUseCase(links);
    }

    public FindLink findLink(final Links links, final Events<LinkVisited> events) {
        return new FindLinkUseCase(links, events);
    }

    public FindAllLinks findAllLinks(final Links links) {
        return new FindAllLinksUseCase(links);
    }

}
