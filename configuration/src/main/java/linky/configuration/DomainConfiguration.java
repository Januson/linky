package linky.configuration;

import linky.infrastructure.Events;
import linky.links.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {

    private final LinksConfiguration links = new LinksConfiguration();

    @Bean
    Events<LinkVisited> events() {
        return new FakeEvents();
    }

    @Bean
    CreateNewLink createNewLink(final Links links, final IsNameUsed isNameUsed) {
        return this.links.createNewLink(links, isNameUsed);
    }

    @Bean
    FindLink findLink(final Links links, final Events<LinkVisited> events) {
        return this.links.findLink(links, events);
    }

    @Bean
    FindAllLinks findAllLinks(final Links links) {
        return this.links.findAllLinks(links);
    }

    static class FakeEvents implements Events<LinkVisited> {

        @Override
        public void fire(LinkVisited event) {

        }
    }

}
