package linky.geo;

import dev.failsafe.CircuitBreaker;
import dev.failsafe.CircuitBreakerBuilder;
import dev.failsafe.CircuitBreakerOpenException;
import dev.failsafe.Failsafe;
import dev.failsafe.FailsafeExecutor;
import dev.failsafe.Fallback;
import dev.failsafe.RetryPolicy;
import dev.failsafe.RetryPolicyBuilder;
import dev.failsafe.Timeout;
import dev.failsafe.TimeoutExceededException;
import java.net.ConnectException;
import java.time.Duration;
import java.util.List;
import linky.visits.GeoEncoder;
import linky.visits.Origin;

public class FailSafeEncoder implements GeoEncoder {

	private final GeoEncoder encoder;
	private final GeoEncoder backupEncoder;

	public FailSafeEncoder(final GeoEncoder encoder, final GeoEncoder backupEncoder) {
		this.encoder = encoder;
		this.backupEncoder = backupEncoder;
	}

	@Override
	public List<Origin.Encoded> encoded(final List<Origin.Pending> origins) {
		final var executor = setupExecutor(origins);
		return executor.get(() -> this.encoder.encoded(origins));
	}

	private FailsafeExecutor<List<Origin.Encoded>> setupExecutor(
			final List<Origin.Pending> origins) {
		return Failsafe.with(fallback(origins), retry(), circuitBreaker(), timeout());
	}

	private Timeout<List<Origin.Encoded>> timeout() {
		return Timeout.of(Duration.ofSeconds(10));
	}

	private CircuitBreaker<List<Origin.Encoded>> circuitBreaker() {
		CircuitBreakerBuilder<List<Origin.Encoded>> builder = CircuitBreaker.builder();
		return builder.withFailureThreshold(3, 10).withSuccessThreshold(5)
				.withDelay(Duration.ofMinutes(1)).build();
	}

	private RetryPolicy<List<Origin.Encoded>> retry() {
		RetryPolicyBuilder<List<Origin.Encoded>> builder = RetryPolicy.builder();
		return builder
				.handle(ConnectException.class, GeoEncodingFailedException.class,
						TimeoutExceededException.class)
				.withDelay(Duration.ofSeconds(1)).withMaxRetries(3).build();
	}

	private Fallback<List<Origin.Encoded>> fallback(final List<Origin.Pending> origins) {
		return Fallback.builder(() -> this.backupEncoder.encoded(origins))
				.handle(CircuitBreakerOpenException.class).build();
	}
}
