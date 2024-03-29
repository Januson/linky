package linky.events;

import static java.util.Objects.requireNonNull;

import linky.links.LinkVisited;
import linky.visits.AddNewVisit;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
class LinkVisitedListener {

	private final AddNewVisit visits;

	LinkVisitedListener(final AddNewVisit visits) {
		this.visits = requireNonNull(visits);
	}

	@Async
	@EventListener
	public void handleAsyncEvent(final LinkVisited event) {
		this.visits.add(event);
	}
}
