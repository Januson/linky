package linky.web.visits;

import linky.links.LinkVisited;
import linky.links.Name;
import linky.visits.FindAllVisits;
import linky.visits.Origin;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
public class FindAllVisitsEndpoint {

    private final FindAllVisits useCase;
    DateTimeFormatter formatter =
        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withLocale(Locale.getDefault())
            .withZone(ZoneId.systemDefault());

    public FindAllVisitsEndpoint(final FindAllVisits useCase) {
        this.useCase = useCase;
    }

    @GetMapping(
        value = "/visits/{linkName}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VisitDto>> allOf(final @PathVariable String linkName) {
        return ResponseEntity.ok(
            createResponse(this.useCase.allOf(new Name(linkName)))
        );
    }

    private List<VisitDto> createResponse(final Collection<LinkVisited> visits) {
        return visits.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    private VisitDto toDto(final LinkVisited visit) {
        return new VisitDto(
            visit.destination().toString(),
            formatter.format(visit.visitedAt()),
            toDto(visit.origin())
        );
    }

    private OriginDto toDto(final Origin origin) {
        return new OriginDto(
            origin.geoEncoding().name(),
            origin.ip().toString(),
            origin.country().toString()
        );
    }

}
