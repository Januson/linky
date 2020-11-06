package linky.visits;

import linky.links.Ip;
import linky.links.Link;
import linky.links.LinkVisited;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NewVisitTest {

    @Test
    void validVisit() {
        LinkVisited newVisit = new LinkVisited(new Ip(), new Link.Name("destination"));
        FakeVisits visits = new FakeVisits();
        new AddANewVisit(visits).add(newVisit);

        assertThat(visits.lastVisit())
            .isEqualTo(newVisit);
    }

}
