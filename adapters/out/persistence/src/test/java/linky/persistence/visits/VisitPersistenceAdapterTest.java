package linky.persistence.visits;

import static org.assertj.core.api.Assertions.assertThat;

import linky.links.Ip;
import linky.links.LinkVisited;
import linky.links.Name;
import linky.visits.Origin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

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

		LinkVisited visit = new LinkVisited(new Name("stckowfl"),
				new Origin.Pending(new Ip("8.8.8.8")));

		adapter.add(visit);

		assertThat(this.links.count()).isEqualTo(1);
	}
}
