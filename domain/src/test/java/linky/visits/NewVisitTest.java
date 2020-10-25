package linky.visits;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NewVisitTest {

    @Test
    void validVisit() {
        NewVisit newVisit = new NewVisit("origin", "destination");
        FakeVisits visits = new FakeVisits();
        new AddANewVisit(visits).add(newVisit);

        assertThat(visits.lastVisit())
            .isEqualTo(newVisit);
    }

    class AddANewVisit {
        private final Visits visits;

        public AddANewVisit(final Visits visits) {
            this.visits = visits;
        }

        public void add(final NewVisit newVisit) {
            this.visits.add(newVisit);
        }
    }

    private static class FakeVisits implements Visits {
        private NewVisit lastVisit;

        public NewVisit lastVisit() {
            return this.lastVisit;
        }

        @Override
        public void add(NewVisit newVisit) {
            this.lastVisit = newVisit;
        }
    }

}
