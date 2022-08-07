package linky.links;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class Url {
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

    public static class Unvalidated {

        private final String url;

        public Unvalidated(final String url) {
            if (url == null) {
                throw new IllegalArgumentException("Url cannot be null!");
            }
            if (url.isBlank()) {
                throw new IllegalArgumentException("Url cannot be blank!");
            }
            this.url = url;
        }

        public Url valid() {
            if (isInvalid(this.url)) {
                throw new MalformedUrl(this.url);
            }
            return new Url(this.url);
        }

        private boolean isInvalid(final String validatedUrl) {
            try {
                new URL(validatedUrl).toURI();
                return false;
            } catch (final MalformedURLException | URISyntaxException exception) {
                return true;
            }
        }
    }

    public static class MalformedUrl extends RuntimeException {
        public MalformedUrl(final String url) {
            super(String.format("Url is invalid: %s!", url));
        }
    }
}
