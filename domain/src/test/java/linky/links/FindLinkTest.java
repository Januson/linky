package linky.links;

import linky.infrastructure.Events;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FindLinkTest {

    @Test
    void unknownLink() {
        final var links = new InMemoryLinks();
        final var events = new DummyEvents();
        final var useCase = new FindLinkUseCase(links, events);
        final var unknownLinkName = new Link.Name("unknown");

        final var link = useCase.findBy(unknownLinkName);

        assertThat(link).isEmpty();
    }

    @Test
    void linkFound() {
        final var links = new InMemoryLinks();
        final var validName = new Link.Name("unknown");
        NewLink newLink = new NewLink(validName, url);
        links.add(newLink);
        final var events = new DummyEvents();
        final var useCase = new FindLinkUseCase(links, events);

        final var link = useCase.findBy(validName);

        assertThat(link)
            .isPresent()
            .isEqualTo(newLink);
    }

    private static class DummyEvents implements Events<LinkVisited> {

        @Override
        public void fire(final LinkVisited event) {
            throw new IllegalStateException("Event should not be fired");
        }

    }

}
