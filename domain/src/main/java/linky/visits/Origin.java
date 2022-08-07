package linky.visits;

import linky.links.Ip;

public interface Origin {

    enum GeoEncoding {
        PENDING,
        DONE
    }

    GeoEncoding geoEncoding();

    Ip ip();

    Country country();

    class Pending implements Origin {

        private final Ip ip;

        public Pending(final Ip ip) {
            this.ip = ip;
        }

        @Override
        public GeoEncoding geoEncoding() {
            return GeoEncoding.PENDING;
        }

        @Override
        public Ip ip() {
            return this.ip;
        }

        @Override
        public Country country() {
            return new Country("unknown");
        }
    }

    class Encoded implements Origin {
        private final Ip ip;
        private final Country country;

        public Encoded(final Ip ip, final Country country) {
            this.ip = ip;
            this.country = country;
        }

        @Override
        public GeoEncoding geoEncoding() {
            return GeoEncoding.DONE;
        }

        @Override
        public Ip ip() {
            return this.ip;
        }

        @Override
        public Country country() {
            return this.country;
        }
    }
}
