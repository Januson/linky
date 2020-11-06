package linky.links;

import java.util.Objects;

public class Link {

    private final Name name;
    private final Url url;

    public Link(Name name, Url url) {
        this.name = name;
        this.url = url;
    }

    public Url url() {
        return this.url;
    }

    public Name name() {
        return this.name;
    }

    public static class Url {
        private final String text;

        public Url(final String text) {
            this.text = text;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final Url url = (Url) o;
            return text.equals(url.text);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text);
        }

        @Override
        public String toString() {
            return this.text;
        }
    }

    public static class Name {
        private final String text;

        public Name(final String text) {
            this.text = text;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final Name name = (Name) o;
            return text.equals(name.text);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text);
        }

        @Override
        public String toString() {
            return this.text;
        }
    }
}