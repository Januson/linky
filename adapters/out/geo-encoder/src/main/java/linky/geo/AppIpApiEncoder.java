package linky.geo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class AppIpApiEncoder implements GeoEncoder {

    private static final Logger LOG = LoggerFactory.getLogger(AppIpApiEncoder.class);

    private final String host;

    public AppIpApiEncoder() {
        this("https://app.ipapi.co");
    }

    public AppIpApiEncoder(String host) {
        this.host = host;
    }

    @Override
    public List<Origin.Encoded> encoded(final List<Origin.Pending> origins) {
        final var ips = origins.stream()
            .map(Origin.Pending::ip)
            .map(Objects::toString)
            .collect(Collectors.joining(","));
        final var form = Map.ofEntries(
            Map.entry("ip_bulk", ips),
            Map.entry("output", "json")
        )
            .entrySet()
            .stream()
            .map(entry -> String.join("=",
                URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8),
                URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
            ).collect(Collectors.joining("&"));

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.host + "/bulk/"))
            .headers("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(form)).build();
        HttpResponse<byte[]> response = getHttpResponse(client, request);

        return toOrigin(fromByteArray(response.body()).getData());
    }

    private HttpResponse<byte[]> getHttpResponse(HttpClient client, HttpRequest request) {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        } catch (IOException e) {
            LOG.warn("Could not reach the encoding service!", e);
            throw new GeoEncodingFailedException();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.warn("Thread was interrupted!", e);
            throw new GeoEncodingFailedException();
        }
    }

    private Origin.Encoded toOrigin(ApiResponse apiResponse) {
        return new Origin.Encoded(
            new Ip(apiResponse.getIp()),
            new Country(apiResponse.getCountryName())
        );
    }

    private List<Origin.Encoded> toOrigin(List<ApiResponse> responses) {
        return responses.stream()
            .map(this::toOrigin)
            .collect(Collectors.toList());
    }

    private static class ApiResponse {
        private final String countryName;
        private final String ip;

        @JsonCreator
        ApiResponse(
            @JsonProperty("country_name") final String countryName,
            @JsonProperty("ip") final String ip) {
            this.countryName = countryName;
            this.ip = ip;
        }

        public String getCountryName() {
            return countryName;
        }

        public String getIp() {
            return ip;
        }
    }

    private Reps fromByteArray(byte[] ips) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(ips, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    static class Reps {
        private final String countIn;
        private final String countOut;
        private final List<ApiResponse> data;

        @JsonCreator
        public Reps(
            @JsonProperty("count_in") String countIn,
            @JsonProperty("count_out") String countOut,
            @JsonProperty("data") List<ApiResponse> data) {
            this.countIn = countIn;
            this.countOut = countOut;
            this.data = data;
        }

        public String getCountIn() {
            return countIn;
        }

        public String getCountOut() {
            return countOut;
        }

        public List<ApiResponse> getData() {
            return data;
        }
    }

}
