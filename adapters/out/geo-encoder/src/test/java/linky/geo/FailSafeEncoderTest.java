package linky.geo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.concurrent.TimeUnit;
import linky.links.Ip;
import linky.visits.GeoEncoder;
import linky.visits.Origin;
import org.junit.jupiter.api.Test;

class FailSafeEncoderTest {

	@Test
	void timeout() {
		final var origins = List.of(new Origin.Pending(new Ip("8.8.8.8")));
		final var failingEncoder = new SleepingEncoder();
		final var backupEncoder = new NoOpEncoder();
		final var encoder = new FailSafeEncoder(failingEncoder, backupEncoder);

		final var encodedOrigins = encoder.encoded(origins);

		assertThat(encodedOrigins).isEmpty();
	}

	@Test
	void retrySuccess() {
		final var origins = List.of(new Origin.Pending(new Ip("8.8.8.8")));
		final var failingEncoder = new FailingEncoder();
		final var backupEncoder = new CountingEncoder();
		final var encoder = new FailSafeEncoder(failingEncoder, backupEncoder);

		encoder.encoded(origins);

		assertThat(failingEncoder.invocations()).isEqualTo(3);
		assertThat(backupEncoder.invocations()).isEqualTo(1);
	}

	@Test
	void retryFail() {
		final var origins = List.of(new Origin.Pending(new Ip("8.8.8.8")));
		final var failingEncoder = new FailingEncoder();
		final var backupEncoder = new FailingEncoder();
		final var encoder = new FailSafeEncoder(failingEncoder, backupEncoder);

		assertThatThrownBy(() -> encoder.encoded(origins))
				.isInstanceOf(GeoEncodingFailedException.class);

		assertThat(failingEncoder.invocations()).isEqualTo(3);
		assertThat(backupEncoder.invocations()).isEqualTo(1);
	}

	static class FailingEncoder implements GeoEncoder {
		private int invocations = 0;

		@Override
		public List<Origin.Encoded> encoded(List<Origin.Pending> origins) {
			this.invocations++;
			throw new GeoEncodingFailedException("Test failed");
		}

		public int invocations() {
			return this.invocations;
		}
	}

	static class SleepingEncoder implements GeoEncoder {
		@Override
		public List<Origin.Encoded> encoded(List<Origin.Pending> origins) {
			try {
				TimeUnit.SECONDS.sleep(11);
			} catch (InterruptedException e) {
				throw new GeoEncodingFailedException("Test failed");
			}
			return List.of();
		}
	}

	static class NoOpEncoder implements GeoEncoder {
		@Override
		public List<Origin.Encoded> encoded(List<Origin.Pending> origins) {
			return List.of();
		}
	}
}
