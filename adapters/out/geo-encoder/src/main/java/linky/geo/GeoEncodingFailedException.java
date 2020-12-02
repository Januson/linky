package linky.geo;

public class GeoEncodingFailedException extends RuntimeException {

    public GeoEncodingFailedException(String message) {
        super(message);
    }

    public GeoEncodingFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
