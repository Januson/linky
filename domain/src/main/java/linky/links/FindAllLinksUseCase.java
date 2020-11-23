package linky.links;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
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
