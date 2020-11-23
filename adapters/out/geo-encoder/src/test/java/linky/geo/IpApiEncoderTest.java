package linky.geo;

import com.github.tomakehurst.wiremock.WireMockServer;
import linky.links.Ip;
import linky.visits.Origin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

class IpApiEncoderTest {

    private static final String BODY = "[{\"country\": \"United States\",\"query\": \"208.80.152.201\"},{\"country\": \"United States\",\"query\": \"8.8.8.8\"},{\"country\": \"Canada\",\"query\": \"24.48.0.1\"}]";
    private WireMockServer wireMockServer;

    @BeforeEach
    public void setup() {
        this.wireMockServer = new WireMockServer(options().port(8081));
        this.wireMockServer.stubFor(post(urlEqualTo("/batch?fields=country,query"))
            .willReturn(aResponse()
                .withBody(BODY.getBytes(StandardCharsets.UTF_8))));
        this.wireMockServer.start();
    }

    @Test
    void successfulRequest() {
        final var origins = createOrigins();
        final var encoder = new IpApiEncoder(this.wireMockServer.baseUrl());

        final var encodedOrigins = encoder.encoded(origins);

        assertThat(encodedOrigins).hasSize(3);
    }

    private List<Origin.Pending> createOrigins() {
        return Stream.of(
            "208.80.152.201",
            "8.8.8.8",
            "24.48.0.1"
        )
            .map(Ip::new)
            .map(Origin.Pending::new)
            .collect(Collectors.toList());
    }

}
