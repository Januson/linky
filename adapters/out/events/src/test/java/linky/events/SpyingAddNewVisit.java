package linky.events;

import linky.links.LinkVisited;
import linky.visits.AddNewVisit;

final class SpyingAddNewVisit implements AddNewVisit {

	private LinkVisited lastAdded;

	@Override
	public void add(final LinkVisited newVisit) {
		this.lastAdded = newVisit;
	}

	public LinkVisited lastAdded() {
		return this.lastAdded;
	}
}
