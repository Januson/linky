package linky.web.links;

import linky.links.FindLink;
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

    @GetMapping(
        value = "/links/{name}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LinkDto> findByName(@PathVariable String name) {
        return this.useCase.findBy(new Name(name))
            .map(this::ok)
            .orElseThrow(() -> notFound(name));
    }

    private ResponseEntity<LinkDto> ok(final Link link) {
        return ResponseEntity.ok(
            new LinkDto(
                link.name().toString(),
                link.url().toString()
            ));
    }

    private NotFoundException notFound(final String linkName) {
        return new NotFoundException(
            String.format("Link with name: %s was not found.", linkName)
        );
    }

}
