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
        final var unknownLinkName = new Name("unknown");

        final var link = useCase.findBy(unknownLinkName, new Ip("unknown"));

        assertThat(link).isEmpty();
    }

    @Test
    void linkFound() {
        final var links = new InMemoryLinks();
        final var validName = new Name("test_name");
        final var existingLink = createLink(validName);
        links.add(existingLink);
        final Events<LinkVisited> events = (event) -> {};
        final var useCase = new FindLinkUseCase(links, events);

        final var link = useCase.findBy(validName, new Ip("unknown"));

        assertThat(link)
            .map(Link::name)
            .hasValue(validName);
    }

    @Test
    void firesAnEvent() {
        final var links = new InMemoryLinks();
        final var validName = new Name("unknown");
        Link newLink = createLink(validName);
        links.add(newLink);
        final SpyingEvents events = new SpyingEvents();
        final var useCase = new FindLinkUseCase(links, events);

        useCase.findBy(validName, new Ip("unknown"));

        assertThat(events.lastEvent())
            .isNotNull();
    }

    private Link createLink(Name validName) {
        return new Link(validName, new Url("test_url"));
    }

    private static class DummyEvents implements Events<LinkVisited> {


        @Override
        public void fire(final LinkVisited event) {
            throw new IllegalStateException("Event should not be fired");
        }

    }

    private static class SpyingEvents implements Events<LinkVisited> {

        private LinkVisited lastEvent;

        @Override
        public void fire(final LinkVisited event) {
            this.lastEvent = event;
        }

        public LinkVisited lastEvent() {
            return lastEvent;
        }
    }

}
