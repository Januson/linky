package linky.events;

import static java.util.Objects.requireNonNull;

import linky.infrastructure.Events;
import linky.links.LinkVisited;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class LinkVisitPublisher implements Events<LinkVisited> {

	private final ApplicationEventPublisher publisher;

	LinkVisitPublisher(final ApplicationEventPublisher publisher) {
		this.publisher = requireNonNull(publisher);
	}

	@Override
	public void fire(final LinkVisited event) {
		this.publisher.publishEvent(event);
	}
}
