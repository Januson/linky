package linky.links;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class FindAllLinksTest {

    @Test
    void noLinksAvailable() {
        final var links = new InMemoryLinks();
        final var useCase = new FindAllLinksUseCase(links);

        final var foundLinks = useCase.all();

        assertThat(foundLinks).isEmpty();
    }

    @Test
    void linksFound() {
        final var links = new InMemoryLinks();
        final int expectedNumberOfLinks = 3;
        createLinks(links, expectedNumberOfLinks);
        final var useCase = new FindAllLinksUseCase(links);

        final var link = useCase.all();

        assertThat(link).hasSize(expectedNumberOfLinks);
    }

    private void createLinks(final Links links, final int n) {
        IntStream.range(0, n).mapToObj(this::createLink).forEach(links::add);
    }

    private Link createLink(final int n) {
        return new Link(new Name("link_" + n), new Url("test_url"));
    }
}
