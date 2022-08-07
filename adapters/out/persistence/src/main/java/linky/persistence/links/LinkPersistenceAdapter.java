package linky.persistence.links;

import java.util.Optional;
import java.util.stream.Stream;
import linky.links.IsNameUsed;
import linky.links.Link;
import linky.links.Links;
import linky.links.Name;
import org.springframework.stereotype.Component;

@Component
class LinkPersistenceAdapter implements Links, IsNameUsed {

	private final LinkRepository links;
	private final LinkMapper mapper;

	LinkPersistenceAdapter(final LinkRepository links, final LinkMapper mapper) {
		this.links = links;
		this.mapper = mapper;
	}

	@Override
	public void add(final Link newLink) {
		this.links.save(this.mapper.toJpaEntity(newLink));
	}

	@Override
	public Optional<Link> findBy(final Name linkName) {
		return this.links.findByShortcut(linkName.toString()).map(this.mapper::toDomainEntity);
	}

	@Override
	public Stream<Link> all() {
		return this.links.findAll().stream().map(this.mapper::toDomainEntity);
	}

	@Override
	public boolean isInUse(final Name linkName) {
		return this.links.existsByShortcut(linkName.toString());
	}
}
