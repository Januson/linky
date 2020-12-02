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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.stream.Collectors;

final class IpApiEncoder extends RemoteGeoEncoder implements GeoEncoder {

    private static final Logger LOG = LoggerFactory.getLogger(IpApiEncoder.class);

    private final String host;

    IpApiEncoder() {
        this("http://ip-api.com");
    }

    IpApiEncoder(final String host) {
        this.host = host;
    }

    @Override
    protected HttpRequest request(final List<Origin.Pending> origins) {
        final var body = bytesOf(ipsOf(origins));
        return HttpRequest.newBuilder()
            .uri(getUri())
            .POST(HttpRequest.BodyPublishers.ofByteArray(body))
            .build();
    }

    @Override
    protected URI getUri() {
        try {
            return new URI(this.host + "/batch?fields=country,query");
        } catch (URISyntaxException e) {
            throw new IllegalStateException();
        }
    }

    @Override
    protected List<Origin.Encoded> toOrigins(final byte[] response) {
        return toOrigin(fromByteArray(response));
    }

    private List<Origin.Encoded> toOrigin(final List<ApiResponse> fromByteArray) {
        return fromByteArray.stream()
            .map(apiResponse -> new Origin.Encoded(
                new Ip(apiResponse.getQuery()),
                new Country(apiResponse.getCountry())
            ))
            .collect(Collectors.toList());
    }

    private List<String> ipsOf(final List<Origin.Pending> origins) {
        return origins.stream()
            .map(Origin.Pending::ip)
            .map(Ip::toString)
            .collect(Collectors.toList());
    }

    private byte[] bytesOf(final List<String> origins) {
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

}
