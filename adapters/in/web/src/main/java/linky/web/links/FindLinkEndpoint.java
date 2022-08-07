package linky.web.links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import linky.links.FindLink;
import linky.links.Ip;
import linky.links.Link;
import linky.links.Name;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindLinkEndpoint {

	private final FindLink useCase;

	public FindLinkEndpoint(final FindLink useCase) {
		this.useCase = useCase;
	}

	@GetMapping(value = "/links/{name}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LinkDto> findByName(final HttpServletRequest request,
			final @PathVariable String name) {
		final var ip = ipAddressOf(request);
		return this.useCase.findBy(new Name(name), ip).map(this::createResponse)
				.orElseThrow(() -> notFound(name));
	}

	private ResponseEntity<LinkDto> createResponse(final Link link) {
		return ResponseEntity.ok(getLinkDto(link.name().toString(), link.url().toString()));
	}

	private LinkDto getLinkDto(final String name, final String url) {
		return new LinkDto(name, url)
				.add(linkTo(methodOn(FindLinkEndpoint.class).findByName(null, name)).withSelfRel())
				.add(linkTo(methodOn(FindAllLinksEndpoint.class).all()).withRel("all_links")
				// ).add(linkTo(
				// methodOn(FindAllVisitsEndpoint.class).allOf(name,
				// null)).withRel("visits")
				);
	}

	private NotFoundException notFound(final String linkName) {
		return new NotFoundException(String.format("Link with name: %s was not found.", linkName));
	}

	public Ip ipAddressOf(final HttpServletRequest request) {
		return ipHeaders().map(header -> header.valueIn(request)).filter(Optional::isPresent)
				.map(Optional::get).findFirst().map(this::toIpAddress)
				// TODO Null Object for unknown ip?
				.orElse(new Ip("unknown"));
	}

	private Ip toIpAddress(String asd) {
		InetAddress byName;
		try {
			byName = InetAddress.getByName(asd);
		} catch (UnknownHostException e) {
			// TODO log
			return new Ip("unknown");
		}
		return new Ip(byName.getHostAddress());
	}

	private Stream<Header> ipHeaders() {
		return Stream.of("X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
				"HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR").map(Header::new);
	}

	private static class Header {
		private final String label;

		Header(final String label) {
			this.label = label;
		}

		Optional<String> valueIn(final HttpServletRequest request) {
			return Optional.ofNullable(request.getHeader(this.label))
					.filter(header -> !header.isBlank())
					.filter(header -> !header.equalsIgnoreCase("unknown"));
		}
	}
}
