package linky.configuration;

import linky.infrastructure.Events;
import linky.links.CreateNewLink;
import linky.links.FindAllLinks;
import linky.links.FindLink;
import linky.links.Link;
import linky.links.LinkVisited;
import linky.links.Links;
import linky.links.LinksConfiguration;
import linky.links.NewLink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Configuration
public class DomainConfiguration {

    private final LinksConfiguration links = new LinksConfiguration();

    @Bean
    Links inMemoryLinks() {
        return new InMemoryLinks();
    }

    @Bean
    Events<LinkVisited> events() {
        return new FakeEvents();
    }

    @Bean
    CreateNewLink createNewLink(final Links links) {
        return this.links.createNewLink(links);
    }

    @Bean
    FindLink findLink(final Links links, final Events<LinkVisited> events) {
        return this.links.findLink(links, events);
    }

    @Bean
    FindAllLinks findAllLinks(final Links links) {
        return this.links.findAllLinks(links);
    }

    static class InMemoryLinks implements Links {

        private final Map<Link.Name, Link> links = new HashMap<>();

        @Override
        public void add(NewLink newLink) {
            this.links.put(newLink.name(), new Link(newLink.name(), new Link.Url("")));
        }

        @Override
        public Optional<Link> findBy(Link.Name linkName) {
            return Optional.ofNullable(this.links.get(linkName));
        }

        @Override
        public Stream<Link> all() {
            return this.links.values().stream();
        }
    }

    class FakeEvents implements Events<LinkVisited> {

        @Override
        public void fire(LinkVisited event) {

        }
    }

}
