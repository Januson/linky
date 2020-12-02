package linky.geo;

import linky.visits.GeoEncoder;
import linky.visits.Origin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

abstract class RemoteGeoEncoder implements GeoEncoder {

    private static final Logger LOG = LoggerFactory.getLogger(RemoteGeoEncoder.class);

    @Override
    public final List<Origin.Encoded> encoded(final List<Origin.Pending> origins) {
        final var request = request(origins);
        final var client = HttpClient.newHttpClient();
        final var response = getHttpResponse(request, client);
        return toOrigins(response.body());
    }

    private HttpResponse<byte[]> getHttpResponse(final HttpRequest request, final HttpClient client) {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        } catch (IOException e) {
            throw new GeoEncodingFailedException("Could not reach the encoding service!", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new GeoEncodingFailedException("Thread was interrupted!", e);
        }
    }

    protected abstract HttpRequest request(final List<Origin.Pending> origins);

    protected abstract URI getUri();

    protected abstract List<Origin.Encoded> toOrigins(final byte[] response);

}
