package linky.links;

import java.time.Instant;

public class LinkVisited {

    private final Instant at = Instant.now();
    private final Ip origin;
    private final Name destination;

    public LinkVisited(final Ip origin, final Name destination) {
        this.origin = origin;
        this.destination = destination;
    }
}
