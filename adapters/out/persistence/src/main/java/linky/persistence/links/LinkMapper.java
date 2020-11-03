package linky.persistence.links;

import linky.links.Link;
import linky.links.NewLink;
import org.springframework.stereotype.Component;

@Component
class LinkMapper {

    Link toDomainEntity(LinkEntity link) {
        return new Link(
            new Link.Name(link.getName()),
            new Link.Url(link.getUrl())
        );
    }

    LinkEntity toJpaEntity(NewLink link) {
        return new LinkEntity(
            link.name().toString(),
            link.url().toString()
        );
    }

}