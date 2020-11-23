package linky.links;

import java.util.Objects;

public class Ip {

    private final String address;

    public Ip(final String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return this.address;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Ip ip = (Ip) o;
        return Objects.equals(address, ip.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

}
