package linky.geo;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import linky.links.Ip;
import linky.visits.Origin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppIpApiEncoderTest {

	private static final Path PATH_TO_BODY = Paths
			.get("src/test/resources/app_ip_api_response.json");

	private WireMockServer wireMockServer;

	@BeforeEach
	public void setup() throws IOException {
		final var body = Files.readAllBytes(PATH_TO_BODY);
		this.wireMockServer = new WireMockServer(options().port(8082));
		this.wireMockServer
				.stubFor(post(urlEqualTo("/bulk/")).willReturn(aResponse().withBody(body)));
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
		return Stream.of("208.80.152.201", "8.8.8.8", "24.48.0.1").map(Ip::new)
				.map(Origin.Pending::new).collect(Collectors.toList());
	}
}
