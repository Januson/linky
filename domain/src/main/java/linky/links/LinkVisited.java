package linky.links;

import java.time.Instant;

public class LinkVisited {

    private final Instant at = Instant.now();
    private final Ip origin;
    private final Link.Name destination;

    public LinkVisited(final Ip origin, final Link.Name destination) {
        this.origin = origin;
        this.destination = destination;
    }
}