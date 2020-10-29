package linky.visits;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NewVisitTest {

    @Test
    void validVisit() {
        NewVisit newVisit = new NewVisit("origin", "destination");
        FakeVisits visits = new FakeVisits();
        new AddANewVisit(visits).add(newVisit);

        assertThat(visits.lastVisit())
            .isEqualTo(newVisit);
    }

}
