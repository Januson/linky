package linky.links;

public class Link {

    private Link() {
    }

    public static class Unvalidated {

        private final String url;

        public Unvalidated(final String url) {
            this.url = null;
        }

        public Link valid() {
            if (url == null) {
                throw new ValidationFailed("Link cannot be null!");
            }
            if (url.isEmpty()) {
                throw new ValidationFailed("Link cannot be empty!");
            }
            return new Link();
        }

    }

    public static class ValidationFailed extends RuntimeException {
        public ValidationFailed(final String reason) {
            super(String.format("Link validation failed: %s",reason));
        }
    }

}
