package linky.web.links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import linky.links.FindAllLinks;
import linky.links.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindAllLinksEndpoint {

	private final FindAllLinks useCase;

	public FindAllLinksEndpoint(final FindAllLinks useCase) {
		this.useCase = useCase;
	}

	@GetMapping(value = "/links", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LinkDto>> all() {
		return ResponseEntity.ok(createResponse(this.useCase.all()));
	}

	private List<LinkDto> createResponse(final List<Link> links) {
		return links.stream().map(this::toDto).collect(Collectors.toList());
	}

	private LinkDto toDto(final Link link) {
		return toDto(link.name().toString(), link.url().toString());
	}

	private LinkDto toDto(final String name, final String url) {
		return new LinkDto(name, url)
				.add(linkTo(methodOn(FindLinkEndpoint.class).findByName(null, name)).withSelfRel())
				.add(linkTo(methodOn(FindAllLinksEndpoint.class).all()).withRel("all_links")
				// ).add(linkTo(
				// methodOn(FindAllVisitsEndpoint.class).allOf(name,
				// null)).withRel("visits")
				);
	}
}
