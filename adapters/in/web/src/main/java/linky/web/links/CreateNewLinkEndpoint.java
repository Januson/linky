package linky.web.links;

import linky.links.*;
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
    public ResponseEntity<String> findLinkInfo(@RequestBody CreateNewLinkRequest request) {
        final var linkName = this.useCase.create(
            new NewLink(
                new Name(request.getName()),
                new Url(request.getUrl())));
        final var location = URI.create("/links/" + linkName.toString());
        return ResponseEntity.created(location).build();
    }

}
