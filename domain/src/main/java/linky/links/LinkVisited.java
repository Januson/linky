package linky.links;

import linky.visits.Origin;

import java.time.Instant;
import java.util.Objects;

public class LinkVisited {

    private final Instant at;
    private final Origin origin;
    private final Name destination;

    public LinkVisited(final Name destination, final Origin origin) {
        this(destination, origin, Instant.now());
    }

    public LinkVisited(final Name destination, final Origin origin, final Instant at) {
        this.origin = origin;
        this.destination = destination;
        this.at = at;
    }

    public Instant visitedAt() {
        return this.at;
    }

    public Origin origin() {
        return this.origin;
    }

    public Name destination() {
        return this.destination;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LinkVisited that = (LinkVisited) o;
        return Objects.equals(at, that.at) &&
            Objects.equals(origin, that.origin) &&
            Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(at, origin, destination);
    }

    @Override
    public String toString() {
        return "LinkVisited{" +
            "at=" + at +
            ", origin=" + origin +
            ", destination=" + destination +
            '}';
    }
}
