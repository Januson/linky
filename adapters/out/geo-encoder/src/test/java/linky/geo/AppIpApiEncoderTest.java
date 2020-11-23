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
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

class AppIpApiEncoderTest {

    private static final String BODY = "{\"count_in\": 3,\"count_out\": 3,\"data\": [{\"asn\": \"AS14907\",\"city\": \"Dallas\",\"continent_code\": \"NA\",\"country\": \"US\",\"country_area\": 9629091.0,\"country_calling_code\": \"+1\",\"country_capital\": \"Washington\",\"country_code\": \"US\",\"country_code_iso3\": \"USA\",\"country_name\": \"United States\",\"country_population\": 327167434.0,\"country_tld\": \".us\",\"currency\": \"USD\",\"currency_name\": \"Dollar\",\"in_eu\": false,\"ip\": \"208.80.152.201\",\"languages\": \"en-US,es-US,haw,fr\",\"latitude\": 32.673100,\"longitude\": -96.775500,\"org\": \"WIKIMEDIA\",\"postal\": \"75241\",\"region\": \"Texas\",\"region_code\": \"TX\",\"timezone\": \"America/Chicago\",\"utc_offset\": \"-0600\",\"version\": \"IPv4\"},{\"asn\": \"AS15169\",\"city\": \"Mountain View\",\"continent_code\": \"NA\",\"country\": \"US\",\"country_area\": 9629091.0,\"country_calling_code\": \"+1\",\"country_capital\": \"Washington\",\"country_code\": \"US\",\"country_code_iso3\": \"USA\",\"country_name\": \"United States\",\"country_population\": 327167434.0,\"country_tld\": \".us\",\"currency\": \"USD\",\"currency_name\": \"Dollar\",\"in_eu\": false,\"ip\": \"8.8.8.8\",\"languages\": \"en-US,es-US,haw,fr\",\"latitude\": \"Sign up to access\",\"longitude\": \"Sign up to access\",\"message\": \"Please message us at ipapi.co/trial for full access\",\"org\": \"GOOGLE\",\"postal\": \"Sign up to access\",\"region\": \"California\",\"region_code\": \"CA\",\"timezone\": \"America/Los_Angeles\",\"utc_offset\": \"-0800\",\"version\": \"IPv4\"},{\"asn\": \"AS5769\",\"city\": \"Montreal\",\"continent_code\": \"NA\",\"country\": \"CA\",\"country_area\": 9984670.0,\"country_calling_code\": \"+1\",\"country_capital\": \"Ottawa\",\"country_code\": \"CA\",\"country_code_iso3\": \"CAN\",\"country_name\": \"Canada\",\"country_population\": 37058856.0,\"country_tld\": \".ca\",\"currency\": \"CAD\",\"currency_name\": \"Dollar\",\"in_eu\": false,\"ip\": \"24.48.0.1\",\"languages\": \"en-CA,fr-CA,iu\",\"latitude\": 45.580800,\"longitude\": -73.582500,\"org\": \"VIDEOTRON\",\"postal\": \"H1S\",\"region\": \"Quebec\",\"region_code\": \"QC\",\"timezone\": \"America/Toronto\",\"utc_offset\": \"-0500\",\"version\": \"IPv4\"}],\"error\": false,\"msg\": \"\"}";
    private WireMockServer wireMockServer;

    @BeforeEach
    public void setup() {
        this.wireMockServer = new WireMockServer(options().port(8082));
        this.wireMockServer.stubFor(post(urlEqualTo("/bulk/"))
            .willReturn(aResponse()
                .withBody(BODY.getBytes(StandardCharsets.UTF_8))));
        this.wireMockServer.start();
    }

    @Test
    void successfulRequest() {
        final var origins = createOrigins();
        final var encoder = new AppIpApiEncoder(this.wireMockServer.baseUrl());

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
