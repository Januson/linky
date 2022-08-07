package linky.visits;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import linky.links.Ip;
import linky.links.IsNameUsed;
import linky.links.LinkNotFound;
import linky.links.LinkVisited;
import linky.links.Name;
import org.junit.jupiter.api.Test;

class NewVisitTest {

    @Test
    void linkDoesNotExist() {
        final var newVisit = new LinkVisited(new Name("destination"), new Origin.Pending(new Ip("8.8.8.8")));
        final var visits = new FakeVisits();
        final IsNameUsed isNameUsed = n -> false;
        final var useCase = new AddNewVisitUseCase(visits, isNameUsed);

        assertThatThrownBy(() -> useCase.add(newVisit)).isInstanceOf(LinkNotFound.class);
    }

    @Test
    void visitSuccessfullyAdded() {
        final var newVisit = new LinkVisited(new Name("destination"), new Origin.Pending(new Ip("8.8.8.8")));
        final var visits = new FakeVisits();
        final IsNameUsed isNameUsed = n -> true;
        final var useCase = new AddNewVisitUseCase(visits, isNameUsed);

        useCase.add(newVisit);

        assertThat(visits.lastVisit()).isEqualTo(newVisit);
    }
}
