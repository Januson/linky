package linky.visits;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import linky.links.Ip;
import linky.links.IsNameUsed;
import linky.links.LinkNotFound;
import linky.links.LinkVisited;
import linky.links.Name;
import org.junit.jupiter.api.Test;

class FindAllVisitsTest {

    @Test
    void noVisits() {
        final var linkName = new Name("destination");
        final IsNameUsed isNameUsed = n -> true;
        final var visits = new FakeVisits();
        final var useCase = new FindAllVisitsUseCase(isNameUsed, visits);

        final var foundVisits = useCase.allOf(linkName);

        assertThat(foundVisits).isEmpty();
    }

    @Test
    void unknownLinkName() {
        final var linkName = new Name("destination");
        final IsNameUsed isNameUsed = n -> false;
        final var visits = new FakeVisits();
        final var useCase = new FindAllVisitsUseCase(isNameUsed, visits);

        assertThatThrownBy(() -> useCase.allOf(linkName)).isInstanceOf(LinkNotFound.class);
    }

    @Test
    void visitsFoundAreOrdered() {
        final var linkName = new Name("destination");
        final IsNameUsed isNameUsed = n -> true;
        final var visits = createVisits(3);
        final var useCase = new FindAllVisitsUseCase(isNameUsed, visits);

        final var foundVisits = useCase.allOf(linkName);

        assertThat(foundVisits).hasSize(3);
        assertThat(foundVisits)
                .extracting(LinkVisited::destination)
                .extracting(Name::toString)
                .containsExactlyElementsOf(List.of("name_2", "name_1", "name_0"));
    }

    private Visits createVisits(int n) {
        final var visits = new InMemoryVisits();
        for (int i = 0; i < n; i++) {
            visits.add(new LinkVisited(
                    new Name("name_" + i),
                    new Origin.Pending(new Ip("8.8.8." + i)),
                    Instant.now().minus(i, ChronoUnit.SECONDS)));
        }
        return visits;
    }
}
