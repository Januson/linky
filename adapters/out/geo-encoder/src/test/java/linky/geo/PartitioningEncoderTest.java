package linky.geo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import linky.links.Ip;
import linky.visits.Origin;
import org.junit.jupiter.api.Test;

class PartitioningEncoderTest {

	private static final int NUMBER_OF_ORIGINS = 201;

	@Test
	void splitOriginsIntoBatches() {
		final var origins = createOrigins();
		final var invocationCountingEncoder = new CountingEncoder();
		final var encoder = new PartitioningEncoder(invocationCountingEncoder);

		final var encodedOrigins = encoder.encoded(origins);

		assertThat(invocationCountingEncoder.invocations()).isEqualTo(3);
		assertThat(encodedOrigins).hasSize(NUMBER_OF_ORIGINS);
	}

	private List<Origin.Pending> createOrigins() {
		return IntStream.range(0, NUMBER_OF_ORIGINS)
				.mapToObj(value -> String.format("208.80.152.%d", value)).map(Ip::new)
				.map(Origin.Pending::new).collect(Collectors.toList());
	}
}
