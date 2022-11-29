package linky.web.links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.List;

import linky.links.FindAllLinks;
import linky.links.Link;

@Controller
public class FindAllLinksEndpoint {

    private final FindAllLinks useCase;

    public FindAllLinksEndpoint(final FindAllLinks useCase) {
        this.useCase = useCase;
    }

    @Get(value = "/links", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<LinkDto>> all() {
        return HttpResponse.ok(createResponse(this.useCase.all()));
    }

    private List<LinkDto> createResponse(final List<Link> links) {
        return links.stream()
            .map(this::toDto)
            .toList();
    }

    private LinkDto toDto(final Link link) {
        return toDto(link.name().toString(), link.url().toString());
    }

    private LinkDto toDto(final String name, final String url) {
        return new LinkDto(name, url);
//            .add(linkTo(methodOn(FindLinkEndpoint.class).findByName(null, name)).withSelfRel())
//            .add(linkTo(methodOn(FindAllLinksEndpoint.class).all()).withRel("all_links")
                // ).add(linkTo(
                // methodOn(FindAllVisitsEndpoint.class).allOf(name,
                // null)).withRel("visits")
//            );
    }
}
