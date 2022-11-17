package linky.web.visits;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import linky.infrastructure.Pagination;
import linky.links.LinkVisited;
import linky.links.Name;
import linky.visits.FindAllVisits;
import linky.visits.Origin;
import linky.web.links.FindLinkEndpoint;

@Controller
public class FindAllVisitsEndpoint {

    private final FindAllVisits useCase;
    private final DateTimeFormatter formatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault());

    public FindAllVisitsEndpoint(final FindAllVisits useCase) {
        this.useCase = useCase;
    }

    @Get(value = "/visits/{linkName}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<VisitDto>> allOf(@PathVariable final String linkName,
        @QueryValue(value = "page", defaultValue = "0") int page,
        @QueryValue(value = "size", defaultValue = "10") int size) {
        final var pagination = new Pagination(page, size);
        return HttpResponse.ok(createResponse(this.useCase.allOf(new Name(linkName))));
    }

    private List<VisitDto> createResponse(final Collection<LinkVisited> visits) {
        return visits.stream().map(this::toDto).collect(Collectors.toList());
    }

    private VisitDto toDto(final LinkVisited visit) {
        final var linkName = visit.destination().toString();
        return new VisitDto(linkName, formatter.format(visit.visitedAt()), toDto(visit.origin())
            // ).add(linkTo(
            // methodOn(FindAllVisitsEndpoint.class)
            // .allOf(linkName, null, null)).withSelfRel()
        ).add(linkTo(methodOn(FindLinkEndpoint.class).findByName(null, linkName))
            .withRel("link_detail"));
    }

    private OriginDto toDto(final Origin origin) {
        return new OriginDto(origin.geoEncoding().name(), origin.ip().toString(),
            origin.country().toString());
    }
}
