package linky.geo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import linky.links.Ip;
import linky.visits.Country;
import linky.visits.GeoEncoder;
import linky.visits.Origin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AppIpApiEncoder extends RemoteGeoEncoder implements GeoEncoder {

	private static final Logger LOG = LoggerFactory.getLogger(AppIpApiEncoder.class);

	private final String host;

	AppIpApiEncoder() {
		this("https://app.ipapi.co");
	}

	AppIpApiEncoder(final String host) {
		this.host = host;
	}

	@Override
	protected HttpRequest request(List<Origin.Pending> origins) {
		final var ips = origins.stream().map(Origin.Pending::ip).map(Objects::toString)
				.collect(Collectors.joining(","));

		final var form = Map.ofEntries(Map.entry("ip_bulk", ips), Map.entry("output", "json"))
				.entrySet().stream()
				.map(entry -> String.join("=",
						URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8),
						URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8)))
				.collect(Collectors.joining("&"));

		return HttpRequest.newBuilder().uri(getUri())
				.headers("Content-Type", "application/x-www-form-urlencoded")
				.POST(HttpRequest.BodyPublishers.ofString(form)).build();
	}

	@Override
	protected URI getUri() {
		return URI.create(this.host + "/bulk/");
	}

	@Override
	protected List<Origin.Encoded> toOrigins(byte[] response) {
		return toOrigin(inflated(response).getData());
	}

	private Origin.Encoded toOrigin(final ApiResponse apiResponse) {
		return new Origin.Encoded(new Ip(apiResponse.getIp()),
				new Country(apiResponse.getCountryName()));
	}

	private List<Origin.Encoded> toOrigin(final List<ApiResponse> responses) {
		return responses.stream().map(this::toOrigin).collect(Collectors.toList());
	}

	private Reps inflated(final byte[] ips) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			return objectMapper.readValue(ips, new TypeReference<>() {});
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}

	private static class ApiResponse {
		private final String countryName;
		private final String ip;

		@JsonCreator
		ApiResponse(@JsonProperty("country_name") final String countryName,
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

	private static class Reps {
		private final List<ApiResponse> data;

		@JsonCreator
		public Reps(@JsonProperty("data") List<ApiResponse> data) {
			this.data = data;
		}

		public List<ApiResponse> getData() {
			return data;
		}
	}
}
