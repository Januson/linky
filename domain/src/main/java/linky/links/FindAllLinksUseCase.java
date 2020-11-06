package linky.links;

import linky.infrastructure.Events;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class FindAllLinksUseCase implements FindAllLinks {

    private final Links links;

    FindAllLinksUseCase(final Links links) {
        this.links = links;
    }

    @Override
    public List<Link> all() {
        return this.links.all()
            .collect(Collectors.toList());
    }
}
