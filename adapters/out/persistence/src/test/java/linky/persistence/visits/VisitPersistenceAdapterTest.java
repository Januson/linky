package linky.persistence.visits;

import linky.links.Ip;
import linky.links.LinkVisited;
import linky.links.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({VisitPersistenceAdapter.class, VisitMapper.class})
class VisitPersistenceAdapterTest {

    @Autowired
    private VisitPersistenceAdapter adapter;

    @Autowired
    private VisitsRepository links;

    @Test
    void storeNewVisit() {
        assertThat(this.links.count()).isZero();

        LinkVisited visit = new LinkVisited(
            new Ip(),
            new Name("stckowfl")
        );

        adapter.add(visit);

        assertThat(this.links.count()).isEqualTo(1);
    }

}