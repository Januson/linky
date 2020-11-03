package linky.persistence.links;

import linky.links.Link;
import linky.links.Links;
import linky.links.NewLink;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class LinkPersistenceAdapter implements Links {

    private final LinkRepository links;
    private final LinkMapper mapper;

    LinkPersistenceAdapter(final LinkRepository links,
                           final LinkMapper mapper) {
        this.links = links;
        this.mapper = mapper;
    }

    @Override
    public void add(final NewLink newLink) {
        this.links.save(this.mapper.toJpaEntity(newLink));
    }

    @Override
    public Optional<Link> findBy(final Link.Name linkName) {
        return this.links
            .findByName(linkName.toString())
            .map(this.mapper::toDomainEntity);
    }

}