package linky.geo;

import linky.visits.GeoEncoder;
import linky.visits.Origin;
import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.CircuitBreakerOpenException;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.FailsafeExecutor;
import net.jodah.failsafe.Fallback;
import net.jodah.failsafe.RetryPolicy;
import net.jodah.failsafe.Timeout;
import net.jodah.failsafe.TimeoutExceededException;

import java.net.ConnectException;
import java.time.Duration;
import java.util.List;

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

    private FailsafeExecutor<List<Origin.Encoded>> setupExecutor(final List<Origin.Pending> origins) {
        return Failsafe.with(
            fallback(origins),
            retry(),
            circuitBreaker(),
            timeout()
        );
    }

    private Timeout<List<Origin.Encoded>> timeout() {
        return Timeout.of(Duration.ofSeconds(10));
    }

    private CircuitBreaker<List<Origin.Encoded>> circuitBreaker() {
        return new CircuitBreaker<List<Origin.Encoded>>()
            .withFailureThreshold(3, 10)
            .withSuccessThreshold(5)
            .withDelay(Duration.ofMinutes(1));
    }

    private RetryPolicy<List<Origin.Encoded>> retry() {
        return new RetryPolicy<List<Origin.Encoded>>()
            .handle(
                ConnectException.class,
                GeoEncodingFailedException.class,
                TimeoutExceededException.class
            )
            .withDelay(Duration.ofSeconds(1))
            .withMaxRetries(3);
    }

    private Fallback<List<Origin.Encoded>> fallback(final List<Origin.Pending> origins) {
        return Fallback.of(() -> this.backupEncoder.encoded(origins))
            .handle(CircuitBreakerOpenException.class);
    }

}
