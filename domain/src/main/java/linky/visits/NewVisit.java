package linky.visits;

import java.time.Instant;

public final class NewVisit {
    private final Instant at = Instant.now();
    private final String origin;
    private final String destination;

    NewVisit(final String origin, final String destination) {
        this.origin = origin;
        this.destination = destination;
    }
}
