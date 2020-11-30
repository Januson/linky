package linky.geo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import linky.links.Ip;
import linky.visits.Country;
import linky.visits.GeoEncoder;
import linky.visits.Origin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

final class IpApiEncoder implements GeoEncoder {
    private final String host;

    private static final Logger LOG = LoggerFactory.getLogger(IpApiEncoder.class);

    IpApiEncoder() {
        this("http://ip-api.com");
    }

    IpApiEncoder(final String host) {
        this.host = host;
    }

    @Override
    public List<Origin.Encoded> encoded(List<Origin.Pending> origins) {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(getUri())
            .POST(HttpRequest.BodyPublishers.ofByteArray(bytesOf(ipsOf(origins))))
            .build();
        try {
            HttpResponse<byte[]> response = HttpClient
                .newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .send(request, HttpResponse.BodyHandlers.ofByteArray());
            return toOrigin(fromByteArray(response.body()));
        } catch (IOException e) {
            LOG.warn("Could not reach the encoding service!", e);
            throw new GeoEncodingFailedException();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.warn("Thread was interrupted!", e);
            throw new GeoEncodingFailedException();
        }
    }

    private URI getUri() {
        try {
            return new URI(this.host + "/batch?fields=country,query");
        } catch (URISyntaxException e) {
            throw new IllegalStateException();
        }
    }

    private List<Origin.Encoded> toOrigin(List<ApiResponse> fromByteArray) {
        return fromByteArray.stream()
            .map(apiResponse -> new Origin.Encoded(
                new Ip(apiResponse.getQuery()),
                new Country(apiResponse.getCountry())
            ))
            .collect(Collectors.toList());
    }

    private static class ApiResponse {
        private final String country;
        private final String query;

        @JsonCreator
        ApiResponse(
            @JsonProperty("country") final String country,
            @JsonProperty("query") final String query) {
            this.country = country;
            this.query = query;
        }

        public String getCountry() {
            return country;
        }

        public String getQuery() {
            return query;
        }
    }

    private List<String> ipsOf(final List<Origin.Pending> origins) {
        return origins.stream()
            .map(Origin.Pending::ip)
            .map(Ip::toString)
            .collect(Collectors.toList());
    }

    private byte[] bytesOf(List<String> origins) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsBytes(origins);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException();
        }
    }

    private List<ApiResponse> fromByteArray(byte[] ips) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(ips, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public class JsonBodyHandler<W> implements HttpResponse.BodyHandler<W> {

        private Class<W> wClass;

        public JsonBodyHandler(Class<W> wClass) {
            this.wClass = wClass;
        }

        @Override
        public HttpResponse.BodySubscriber<W> apply(HttpResponse.ResponseInfo responseInfo) {
            return asJSON(wClass);
        }

        public <T> HttpResponse.BodySubscriber<T> asJSON(Class<T> targetType) {
            HttpResponse.BodySubscriber<String> upstream = HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);

            return HttpResponse.BodySubscribers.mapping(
                upstream,
                (String body) -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        return objectMapper.readValue(body, targetType);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
        }
    }


}
