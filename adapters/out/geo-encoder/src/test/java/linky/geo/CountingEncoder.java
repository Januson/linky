package linky.geo;

import java.util.List;
import java.util.stream.Collectors;
import linky.visits.Country;
import linky.visits.GeoEncoder;
import linky.visits.Origin;

class CountingEncoder implements GeoEncoder {

	private int invocations = 0;

	@Override
	public List<Origin.Encoded> encoded(List<Origin.Pending> origins) {
		this.invocations++;
		return origins.stream()
				.map(pending -> new Origin.Encoded(pending.ip(), new Country("unknown")))
				.collect(Collectors.toList());
	}

	int invocations() {
		return this.invocations;
	}
}
