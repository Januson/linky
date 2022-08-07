package linky.geo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import linky.visits.GeoEncoder;
import linky.visits.Origin;

public class PartitioningEncoder implements GeoEncoder {

	private static final int BATCH_SIZE = 100;
	private final GeoEncoder encoder;

	public PartitioningEncoder(final GeoEncoder encoder) {
		this.encoder = encoder;
	}

	@Override
	public List<Origin.Encoded> encoded(List<Origin.Pending> all) {
		return new PartitionBy<>(all, BATCH_SIZE).stream().map(this.encoder::encoded)
				.flatMap(Collection::stream).collect(Collectors.toList());
	}
}
