package linky.links;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

@Transactional
class FindAllLinksUseCase implements FindAllLinks {

    private final Links links;

    FindAllLinksUseCase(final Links links) {
        this.links = links;
    }

    @Override
    public List<Link> all() {
        return this.links.all().toList();
    }
}
