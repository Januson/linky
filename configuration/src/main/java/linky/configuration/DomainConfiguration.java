package linky.configuration;

import linky.infrastructure.Events;
import linky.links.CreateNewLink;
import linky.links.FindAllLinks;
import linky.links.FindLink;
import linky.links.IsNameUsed;
import linky.links.LinkVisited;
import linky.links.Links;
import linky.links.LinksConfiguration;
import linky.visits.AddNewVisit;
import linky.visits.EncodePendingVisits;
import linky.visits.FindAllVisits;
import linky.visits.GeoEncoder;
import linky.visits.PendingOrigins;
import linky.visits.Visits;
import linky.visits.VisitsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {

	private final LinksConfiguration links = new LinksConfiguration();
	private final VisitsConfiguration visits = new VisitsConfiguration();

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

	@Bean
	AddNewVisit addNewVisit(final Visits visits, final IsNameUsed isNameUsed) {
		return this.visits.addNewVisit(visits, isNameUsed);
	}

	@Bean
	EncodePendingVisits encodePendingVisits(final PendingOrigins pendingOrigins,
			final GeoEncoder geoEncoder) {
		return this.visits.encodePendingVisits(pendingOrigins, geoEncoder);
	}

	@Bean
	FindAllVisits findAllVisits(final Visits visits, final IsNameUsed isNameUsed) {
		return this.visits.findAllVisits(visits, isNameUsed);
	}
}
