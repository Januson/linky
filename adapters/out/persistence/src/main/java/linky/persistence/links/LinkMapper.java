package linky.persistence.links;

import linky.links.Link;
import linky.links.Name;
import linky.links.NewLink;
import linky.links.Url;
import org.springframework.stereotype.Component;

@Component
class LinkMapper {

    Link toDomainEntity(final LinkEntity link) {
        return new Link(
            new Name(link.getName()),
            new Url(link.getUrl())
        );
    }

    LinkEntity toJpaEntity(final Link link) {
        return new LinkEntity(
            link.name().toString(),
            link.url().toString()
        );
    }

}