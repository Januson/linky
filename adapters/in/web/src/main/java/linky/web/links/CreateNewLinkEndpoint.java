package linky.web.links;

import linky.links.CreateNewLink;
import linky.links.Name;
import linky.links.NewLink;
import linky.links.Url;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static java.util.Objects.requireNonNull;

@RestController
class CreateNewLinkEndpoint {

    private final CreateNewLink useCase;

    CreateNewLinkEndpoint(final CreateNewLink useCase) {
        this.useCase = requireNonNull(useCase);
    }

    @PostMapping(
        value = "/links",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LinkDto> create(@RequestBody CreateNewLinkRequest request) {
        final var linkName = this.useCase.create(createNewLink(request));
        final var location = URI.create("/links/" + linkName.toString());
        return ResponseEntity.created(location)
            .body(new LinkDto(linkName.toString(), request.getUrl()));
    }

    private NewLink createNewLink(final CreateNewLinkRequest request) {
        final NewLink newLink;
        if (request.getName() == null) {
            newLink = newLinkWithRandomName(request.getUrl());
        } else {
            newLink = newLinkWithCustomName(request.getName(), request.getUrl());
        }
        return newLink;
    }

    private NewLink newLinkWithCustomName(final String name, final String url) {
        return new NewLink(
            new Name.Unvalidated(name),
            new Url.Unvalidated(url));
    }

    private NewLink newLinkWithRandomName(final String url) {
        return new NewLink(new Url.Unvalidated(url));
    }

}
